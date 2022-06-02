project(magic)
cmake_minimum_required(VERSION 3.18.1)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall")

function(filter_regex _action _regex _listname)
    # check an action
    if("${_action}" STREQUAL "INCLUDE")
        set(has_include TRUE)
    elseif("${_action}" STREQUAL "EXCLUDE")
        set(has_include FALSE)
    else()
        message(FATAL_ERROR "Incorrect value for ACTION: ${_action}")
    endif()

    set(${_listname})
    foreach(element ${ARGN})
        string(REGEX MATCH ${_regex} result ${element})
        if(result)
            if(has_include)
                list(APPEND ${_listname} ${element})
            endif()
        else()
            if(NOT has_include)
                list(APPEND ${_listname} ${element})
            endif()
        endif()
    endforeach()

    # put result in parent scope variable
    set(${_listname} ${${_listname}} PARENT_SCOPE)
endfunction()

set(LIBFILE "../../../../file/src")
include_directories(${LIBFILE})

variable_watch(SOURCES)
file(GLOB_RECURSE SOURCES
    Bindings.c
    ${LIBFILE}/*.c
    ${LIBFILE}/*.h
)

filter_regex(EXCLUDE "memtest.c" SOURCES ${SOURCES})
filter_regex(EXCLUDE "tests/test.c" SOURCES ${SOURCES})

add_library(magic SHARED ${SOURCES})

add_compile_definitions(PRIx64="x")
add_compile_definitions(VERSION="android-bindings")

##https://android.googlesource.com/platform/development/+/47c55f4/ndk/platforms/android-9/include/unistd.h#42
add_compile_definitions(STDIN_FILENO=0)
add_compile_definitions(STDOUT_FILENO=1)
add_compile_definitions(STDERR_FILENO=2)

#https://android.googlesource.com/platform/development/+/47c55f4/ndk/platforms/android-9/include/unistd.h#104
add_compile_definitions(R_OK=4)
add_compile_definitions(W_OK=2)
add_compile_definitions(X_OK=1)
add_compile_definitions(F_OK=0)

add_compile_definitions(HAVE_WCHAR_H)
add_compile_definitions(HAVE_MBRTOWC)
add_compile_definitions(HAVE_WCWIDTH)
add_compile_definitions(HAVE_WCTYPE_H)
