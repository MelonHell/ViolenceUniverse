package ru.melonhell.violenceuniverse.server.session.stage;

import ru.melonhell.violenceuniverse.server.session.Session;

public abstract class NoDataStage implements Stage<Void> {
    @Override
    public final void onEnter(Session session, Void data) {
        onEnter(session);
    }

    protected void onEnter(Session session) {
        Stage.super.onEnter(session, null);
    }
}
