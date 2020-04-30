# Docker Controller

This is an application to programmatically manage the docker containers through a simple HTTP interface.

My initial idea was to be able to start/stop applications running in docker using Amazon's Alexa voice assistant. And, obviously, to practice some clojure. 

## Running

Download the latest `jar` from the releases page and run:

    $ java -jar docker-controller.jar

After you've started the app, you can send some http requests to manage docker containers:
    
    $ curl --request POST \
        --url http://localhost:3000/containers/%my-awesome-app% \
        --header 'content-type: application/json' \
        --data '{"action": "start"}'
    
    $ curl --request POST \
        --url http://localhost:3000/containers/%my-awesome-app% \
        --header 'content-type: application/json' \
        --data '{"action": "stop"}'

By default an application runs on port `3000` and connects to docker's socket at `unix://var/run/docker.sock`
You can change these settings by setting the `PORT` and `UNIX_SOCKET` environment variables, e.g.:

    $ PORT=8080 java -jar docker-controller.jar
    
To me, the most convenient way to keep the app running is using some process control system, e.g. [Supervisor](http://supervisord.org). It would take care of running the app in the background and restoring it after system reboot. 
