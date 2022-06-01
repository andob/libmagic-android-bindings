project(magic)
cmake_minimum_required(VERSION 3.18.1)
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall -DHAVE_CONFIG_H")
include_directories(../../../../file/src)
file(GLOB_RECURSE LIBMAGIC_SOURCES "*.c")
add_library(magic SHARED Bindings.c ${LIBMAGIC_SOURCES})
target_link_libraries(magic log z)