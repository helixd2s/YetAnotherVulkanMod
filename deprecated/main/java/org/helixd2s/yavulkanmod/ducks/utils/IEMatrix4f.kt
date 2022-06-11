package org.helixd2s.yavulkanmod.ducks.utils

interface IEMatrix4f {
    fun loadFromArray(arr: FloatArray?)
    fun toArray(): FloatArray
}