package com.gmail.dzutsevasabina.viewmodel.mapper

interface Mapper<SRC, DST> {
    fun transform(data: SRC): DST
}
