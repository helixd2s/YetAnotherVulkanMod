cmake_minimum_required(VERSION 3.20)
project(YAV VERSION 0.1.0)
set(PROJECT_NAME YAV)

#
MACRO(ADD_MSVC_PRECOMPILED_HEADER PrecompiledHeader PrecompiledSource SourcesVar)
  IF(MSVC)
    GET_FILENAME_COMPONENT(PrecompiledBasename ${PrecompiledHeader} NAME_WE)
    SET(PrecompiledBinary "${CMAKE_CURRENT_BINARY_DIR}/${PrecompiledBasename}.pch")
    SET(Sources ${${SourcesVar}})

    SET_SOURCE_FILES_PROPERTIES(${PrecompiledSource}
                                PROPERTIES COMPILE_FLAGS "/Yc\"${PrecompiledHeader}\" /Fp\"${PrecompiledBinary}\""
                                           OBJECT_OUTPUTS "${PrecompiledBinary}")
    SET_SOURCE_FILES_PROPERTIES(${Sources}
                                PROPERTIES COMPILE_FLAGS "/Yu\"${PrecompiledHeader}\" /FI\"${PrecompiledHeader}\" /Fp\"${PrecompiledBinary}\""
                                           OBJECT_DEPENDS "${PrecompiledBinary}")  
    # Add precompiled header to SourcesVar
    LIST(APPEND ${SourcesVar} ${PrecompiledSource})
  ENDIF(MSVC)
ENDMACRO(ADD_MSVC_PRECOMPILED_HEADER)

# 
find_package(Vulkan REQUIRED FATAL_ERROR)
find_package(JNI REQUIRED)

# 
set(CMAKE_C_STANDARD 23)
set(CMAKE_CXX_STANDARD 23)
set(_src_root_path "${PROJECT_SOURCE_DIR}")
file(GLOB_RECURSE _source_list 
  LIST_DIRECTORIES false
  "${_src_root_path}/../vk-utils/*.inl"
  "${_src_root_path}/../vk-utils/*.hpp"
  "${_src_root_path}/../vk-utils/*.h"
  "${_src_root_path}/../vk-utils/cpp21/*.inl"
  "${_src_root_path}/../vk-utils/cpp21/*.hpp"
  "${_src_root_path}/../vk-utils/cpp21/*.h"
  "${_src_root_path}/../Alter/src/Alter/*.cpp"
  "${_src_root_path}/../Alter/src/Alter/*.c"
  "${_src_root_path}/../Alter/src/Alter/*.inl"
  "${_src_root_path}/../Alter/include/Alter/*.inl"
  "${_src_root_path}/../Alter/include/Alter/*.hpp"
  "${_src_root_path}/../Alter/include/Alter/*.h"
  "${_src_root_path}/../Alter/include/*.h"
  "${_src_root_path}/../Alter/include/*.inl"
  "${_src_root_path}/../Alter/include/*.hpp"
  "${_src_root_path}/../Alter/include/*.cpp"
  "${_src_root_path}/../Alter/include/*.hxx"
  "${_src_root_path}/../Alter/include/*.cxx"
  "${_src_root_path}/src/main/cpp/*.cpp"
  "${_src_root_path}/src/main/cpp/*.hpp"
  "${_src_root_path}/src/main/cpp/*.h"
)

#
SET(PLATFORM_DEF )
set(PLATFORM_HEAD )
IF (WIN32)
  SET (PLATFORM_DEF VK_USE_PLATFORM_WIN32_KHR)
  SET (PLATFORM_HEAD <windows.h>)
ELSE()
  SET (PLATFORM_DEF )
ENDIF()

# 
add_library(${PROJECT_NAME} SHARED ${_source_list})
target_compile_definitions(${PROJECT_NAME} PUBLIC SHARED_PTR_NAMESPACE=std ALT_USE_ROBIN_HOOD USE_CMAKE_PCH VULKAN_HPP_NO_CONSTRUCTORS NOMINMAX ALT_ENABLE_VMA ALT_ENABLE_GLTF CPP21_DEBUG_MEMORY TINYGLTF_USE_CPP14 ANAMED=alter ${PLATFORM_DEF})
target_include_directories(${PROJECT_NAME} PUBLIC 
  ${PROJECT_SOURCE_DIR}/include 
  ${PROJECT_SOURCE_DIR}/../Alter/
  ${PROJECT_SOURCE_DIR}/../Alter/include/
  ${PROJECT_SOURCE_DIR}/../Alter/include/Alter/
  ${PROJECT_SOURCE_DIR}/../vk-utils/
  ${PROJECT_SOURCE_DIR}/../vk-utils/interval-tree/include
  ${PROJECT_SOURCE_DIR}/../vk-utils/submodules/include 
  ${PROJECT_SOURCE_DIR}/../vk-utils/generator/include 
  ${PROJECT_SOURCE_DIR}/../vk-utils/glm
  ${PROJECT_SOURCE_DIR}/../vk-utils/glm/glm
  ${PROJECT_SOURCE_DIR}/../VulkanMemoryAllocator/include
  ${PROJECT_SOURCE_DIR}/../Vulkan-Headers/include
  ${PROJECT_SOURCE_DIR}/../glm/glm
  ${PROJECT_SOURCE_DIR}/../glm
  ${Vulkan_INCLUDE_DIRS}
  ${JNI_INCLUDE_DIRS}
)

#
target_link_directories(${PROJECT_NAME} PUBLIC 
  ${PROJECT_SOURCE_DIR}/../Alter/lib
  ${PROJECT_SOURCE_DIR}/lib
)

#
set (HSOURCES "${PROJECT_SOURCE_DIR}/../vk-utils/cpp21/core.hpp" "${PROJECT_SOURCE_DIR}/../Alter/include/Alter/Core.hpp")

#
#ADD_MSVC_PRECOMPILED_HEADER(
  #"${PROJECT_SOURCE_DIR}/../Alter/include/cmake_pch.hpp"
  #"${PROJECT_SOURCE_DIR}/../Alter/include/cmake_pch.cpp"
  #HSOURCES)

#
target_link_libraries(${PROJECT_NAME} ${CMAKE_DL_LIBS} ${Vulkan_LIBRARIES} ${JNI_LIBRARIES} )

# 
if (MSVC)
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++2b /d1FastFail /std:c17 /std:c++latest /bigobj /MP /MD /FS /O0 /Od /Ob0 /Zc:threadSafeInit /errorReport /arch:AVX2")
    set(CMAKE_EXE_LINKER_CXX_FLAGS "${CMAKE_CXX_FLAGS} /bigobj /MP /MD /MAP /incremental /debug:fastlink /experimental /errorReport /LTCG:PGInstrument")
endif()

# 
foreach(_source IN ITEMS ${_source_list})
    get_filename_component(_source_path "${_source}" PATH)
    file(RELATIVE_PATH _source_path_rel "${_src_root_path}" "${_source_path}")
    string(REPLACE "/" "\\" _group_path "${_source_path_rel}")
    source_group("${_group_path}" FILES "${_source}")
endforeach()
