import com.codahale.metrics.health.HealthCheck.Result
import com.codahale.metrics.health.HealthCheckRegistry
import io.dropwizard.Application
import io.dropwizard.Configuration
import io.dropwizard.setup.Environment
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MediaType
import kotlin.collections.Map.Entry
import kotlin.collections.MutableMap.MutableEntry


fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
    DropwizardApp().run("server", "config/local.yaml")
}

class DropwizardTestConfig(val name: String = "unknown") : Configuration()

class DropwizardApp : Application<DropwizardTestConfig>() {
    override fun run(configuration: DropwizardTestConfig, environment: Environment) {
        println("Running ${configuration.name}!")
        val calculatorComponent = DropwizardAppComponent()
        environment.jersey().register(calculatorComponent)
    }
}

@Path("/")
class DropwizardAppComponent {
    @Path("/add")
    @GET
    fun add(@QueryParam("a") a: Double, @QueryParam("b") b: Double): Double {
        return a + b
    }

    @Path("/multiply")
    @GET
    fun multiply(@QueryParam("a") a: Double, @QueryParam("b") b: Double): Double {
        return a * b
    }

    @Path("/divide")
    @GET
    fun divide(@QueryParam("a") a: Double, @QueryParam("b") b: Double): Double {
        if (b == .0) {
            throw WebApplicationException("Can't divide by zero")
        }
        return a / b
    }
}
