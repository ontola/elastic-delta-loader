/*
 * ORI API
 * Copyright (C), Argu BV
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.ontola.ori.api

import io.ontola.ori.api.context.CtxProps
import io.ontola.ori.api.context.DocumentCtx
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.*
import java.util.*

/**
 * Listens to kafka streams and processes delta streams into elastic.
 */
@ExperimentalCoroutinesApi
fun main(args: Array<String>) = runBlocking {
    val ctx = ORIContext.getCtx()

    printInitMessage(ctx.config)

    var primaryFlag = ""
    if (args.isNotEmpty()) {
        primaryFlag = args[0]
    }

    val cmd = arrayListOf("processDeltas", primaryFlag).joinToString(" ")
    val deltas = launch(coroutineContext) {
        val dataStore = RestHighLevelClient(
            host="10.0.1.144",
            port=9200,
            https=false
        )
        val exists = dataStore.indices().exists(GetIndexRequest("someindex"), RequestOptions.DEFAULT)
        if (!exists) {
            dataStore.indices().create(CreateIndexRequest("someindex"), RequestOptions.DEFAULT)
        }
        processDeltas(DocumentCtx(CtxProps(cmd), dataStore), primaryFlag == "--from-beginning")
    }

    joinAll(deltas)

    joinAll()
}

fun printInitMessage(p: Properties) {
    println("================================================")
    println("Starting ORI API\n")
    val keys = p.keys()
    while (keys.hasMoreElements()) {
        val key = keys.nextElement() as String
        val value = p.get(key)
        println(key.substring("ori.api.".length) + ": " + value)
    }
    println("================================================")
}
