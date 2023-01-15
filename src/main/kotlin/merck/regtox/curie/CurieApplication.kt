package merck.regtox.curie

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CurieApplication

fun main(args: Array<String>) {
	runApplication<CurieApplication>(*args)
}
