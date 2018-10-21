package com.keytotech.pagingdemo.data.models

/**
 * MockComment
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class MockComment {

    fun list(): List<Comment> {
        val res = ArrayList<Comment>()
        for (i in 0..10) {
            res.add(this.comment())
        }
        return res
    }

    private fun comment(): Comment {
        return Comment(
            "fugit labore quia mollitia quas deserunt nostrum sunt",
            100,
            12,
            "ut dolorum nostrum id quia aut est\nfuga est inventore vel eligendi explicabo quis consectetur\naut occaecati repellat id natus quo est\nut blanditiis quia ut vel ut maiores ea",
            "Veronica_Goodwin@timmothy.net"
        )
    }
}