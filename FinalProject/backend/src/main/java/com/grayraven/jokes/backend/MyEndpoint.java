/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.grayraven.jokes.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.grayraven.jokes.Joker;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v2",
  namespace = @ApiNamespace(
    ownerDomain = "backend.jokes.grayraven.com",
    ownerName = "backend.jokes.grayraven.com",
    packagePath=""
  )
)
public class MyEndpoint {

    @ApiMethod(name = "getJoke")
    public MyBean getJoke() {
        MyBean response = new MyBean();
        Joker joker = new Joker();
        String joke = joker.getJoke();
        response.setData(joke);
        return response;
    }

}
