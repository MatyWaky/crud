#!/bin/bash

# List of containers to start/stop
postgres=2a9b70c0a28c
pgadmin4=9781aa9962a6
containers=($postgres $pgadmin4)

echo "Application crud_spring"
# Function to start containers
start_containers() {
    echo "Starting containers..."
    for container in "${containers[@]}"; do
        docker start "$container"
        if [ $? -eq 0 ]; then
            echo "Container $container has been started."
        else
            echo "Error starting container $container."
        fi
    done
}

# Function to stop containers
stop_containers() {
    echo "Stopping containers..."
    for container in "${containers[@]}"; do
        docker stop "$container"
        if [ $? -eq 0 ]; then
            echo "Container $container has been stopped."
        else
            echo "Error stopping container $container."
        fi
    done
}

# Main script loop
echo "Choose an option:"
echo "[1] Start containers"
echo "[2] Stop containers"
echo "[3] Exit"

read -p "Choice: " choice

case $choice in
    1)
        start_containers
        ;;
    2)
        stop_containers
        ;;
    3)
        echo "Exiting script."
        exit 0
        ;;
    *)
        echo "Invalid choice, script is exiting."
        exit 1
        ;;
esac
