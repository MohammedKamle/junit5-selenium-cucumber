#!/bin/bash

mvn test -Dbrowser=safari &  # Run in background
mvn test -Dbrowser=chrome &  # Run in background

wait  # Wait for all background processes to complete


