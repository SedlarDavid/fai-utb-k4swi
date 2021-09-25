cmake_minimum_required( VERSION 3.0 )
project( firstApp )
add_subdirectory(myLib/)
add_executable( firstApp main.cpp )
target_link_libraries(firstApp myLib)


if(WIN32)
    target_compile_definitions(firstApp PUBLIC WINDOWS)
endif()
