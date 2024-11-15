package tierMaker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication class TierMakerApplication

fun main(args: Array<String>) {
  runApplication<TierMakerApplication>(*args)
}
