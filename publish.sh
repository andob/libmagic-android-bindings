set -o allexport

echo "Publishing..."

./gradlew :bindings:publishToMavenLocal
