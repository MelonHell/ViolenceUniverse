package ru.melonhell.violenceuniverse.server.session;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import ru.melonhell.violenceuniverse.common.enums.StageType;
import ru.melonhell.violenceuniverse.common.packets.ClientboundPacket;
import ru.melonhell.violenceuniverse.common.packets.ServerboundPacket;
import ru.melonhell.violenceuniverse.common.packets.clientbound.ClientboundServerErrorPacket;
import ru.melonhell.violenceuniverse.server.exceptions.ViolenceUniverseException;
import ru.melonhell.violenceuniverse.server.session.commandInterceptor.CommandInterceptor;
import ru.melonhell.violenceuniverse.server.session.exceptions.InvalidControllerTypeException;
import ru.melonhell.violenceuniverse.server.session.port.Transport;
import ru.melonhell.violenceuniverse.server.session.stage.LeaveReason;
import ru.melonhell.violenceuniverse.server.session.stage.NoDataStage;
import ru.melonhell.violenceuniverse.server.session.stage.Stage;
import ru.melonhell.violenceuniverse.server.stages.StageService;
import ru.melonhell.violenceuniverse.server.user.entity.User;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Session {
    private final StageService stageService;
    private final Transport transport;
    @SuppressWarnings("rawtypes")
    @NotNull
    @Getter
    private Stage stage;

    @Getter
    @Setter
    private User user;
    private boolean destroyed = false;

    public void setDestroyMarker() {
        this.destroyMarker = true;
    }

    /**
     * Маркер что сессия должна быть уничтоженна после выполнения команды
     */
    @Getter
    private boolean destroyMarker;
    private final PriorityQueue<CommandInterceptor> globalInterceptors = new PriorityQueue<>(Comparator.comparingInt(CommandInterceptor::priority));

    public <T> Session(
            StageService stageService,
            Transport transport,
            @NonNull Stage<T> initStage,
            T data
    ) {
        this.stageService = stageService;
        this.transport = transport;
        this.stage = initStage;
        transport.addCommandCallback(this::processPacket);
        transport.addDisconnectCallback(this::destroy);


        //noinspection unchecked
        this.stage.onEnter(this, data);
    }

    public void sendPacket(ClientboundPacket packet) {
        if (destroyed)
            return;
        transport.sendPacket(packet);
    }

    public boolean isAlive() {
        return transport.isAlive();
    }

    private void processPacket(ServerboundPacket packet) {
        try {
            for (CommandInterceptor interceptor : globalInterceptors) {
                boolean shouldContinue = interceptor.onPacket(this, packet);
                if (!shouldContinue)
                    return;
            }

        } catch (ViolenceUniverseException ex) {
            transport.sendPacket(new ClientboundServerErrorPacket(ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            String message = "Произошла неизвестная ошибка(" + ex.getClass().getSimpleName() + "):" + ex.getMessage();
            transport.sendPacket(new ClientboundServerErrorPacket(message));
            destroy();
        } finally {
            if (destroyMarker)
                destroy();
        }
    }

    public void destroy() {
        if (destroyed)
            return;
        destroyed = true;

        stage.onLeave(this, LeaveReason.DISCONNECT);

        if (isAlive()) {
            transport.disconnect();
        }
    }

    public <T> void setStage(@NotNull Stage<T> stage, T data) {
        if (destroyed)
            return;
        this.stage.onLeave(this, LeaveReason.CHANGE_CONTROLLER);
        this.stage = stage;
        stage.onEnter(this, data);
    }
    public <T extends Stage<D>,D> void setStage(@NotNull Class<T> stage, D data) {
        T stageStage = stageService.getByClass(stage);
        setStage(stageStage,data);
    }

    public void setStage(StageType type, Object data) {
        Stage stage = stageService.getByType(type);
        if (stage == null)
            throw new InvalidControllerTypeException();

        setStage(stage, data);
    }

    public void setStage(Class<? extends NoDataStage> stage) {
        NoDataStage stageStage = stageService.getByClass(stage);

        setStage(stageStage, null);
    }

    public void addGlobalInterceptor(CommandInterceptor consumer) {
        globalInterceptors.add(consumer);
    }
}
