package org.helixd2s.yavulkanmod.ducks.utils

interface IEMatrix3f {
    fun loadFromArray(arr: FloatArray?)
    fun toArray(): FloatArray
}