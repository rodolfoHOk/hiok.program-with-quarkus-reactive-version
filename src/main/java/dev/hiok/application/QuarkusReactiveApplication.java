package dev.hiok.application;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
  tags = {
    @Tag(name = "Fruits", description = "Fruit manager")
  },
  info = @Info(
    title = "Quarkus Reactive Example Application",
    version = "1.0.0",
    contact = @Contact(
      name = "Rudolf HiOK Dev",
      url = "https://github.com/rodolfoHOk/hiok.program-with-quarkus-reactive-version",
      email = "hioktec@gmail.com"
    ),
    license = @License(
      name = "Apache 2.0",
      url = "https://www.apache.org/licenses/LICENSE-2.0.html"
    )
  )
)
public class QuarkusReactiveApplication extends Application {

}
