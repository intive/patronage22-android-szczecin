package com.intive.patronage.retro.board.model.fakeApi

import com.intive.patronage.retro.board.model.entity.BoardRemote

class FakeBoardsApi {
    fun sendBoards() = listOf(
        BoardRemote(0, "Board #1 something something", "Created"),
        BoardRemote(1, "Board #2", "Voting"),
        BoardRemote(2, "Board #3 with a long name which should take two lines on most of the devices", "Actions"),
        BoardRemote(3, "Board #4 with a long name which should take two lines on most of the devices", "Created"),
        BoardRemote(4, "Board #5 something something", "Voting"),
        BoardRemote(
            5,
            "Board #6 with a very very very long name which should take more than two " +
                "lines on most of the devices and should be truncated",
            "Actions"
        ),
        BoardRemote(
            6,
            "Board #7 with a very very very long name which should take more than " +
                "two lines on most of the devices and should be truncated",
            "Created"
        ),
        BoardRemote(7, "Board #8 something something", "Voting"),
        BoardRemote(
            8,
            "Board #9 with a long name which should take two " +
                "lines on most of the devices",
            "Actions"
        ),
        BoardRemote(
            9,
            "Board #10 with a very very very long name which should take more " +
                "than two lines on most of the devices and should be truncated",
            "Actions"
        )
    )
}
