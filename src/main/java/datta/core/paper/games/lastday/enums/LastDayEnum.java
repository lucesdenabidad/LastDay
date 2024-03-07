package datta.core.paper.games.lastday.enums;

import datta.core.paper.games.lastday.LastDayGames;

public enum LastDayEnum {
    DIA {

        @Override
        public Runnable getRunnable() {
            return () -> {
                LastDayGames.start(10,0,3,20,10);
            };
        }
    },
    NOCHE {
        @Override
        public Runnable getRunnable() {
            return () -> {
                LastDayGames.start(10,13000,3,20,10);
            };
        }
    };

    public abstract Runnable getRunnable();
}
