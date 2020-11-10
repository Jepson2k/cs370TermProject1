#!/bin/sh
# This is how Caleb said he was going to test our TP-D2.
curl -X POST -H "Content-Type: application/json" -d "{\"message\": \"Hello There.\" }" localhost:8080