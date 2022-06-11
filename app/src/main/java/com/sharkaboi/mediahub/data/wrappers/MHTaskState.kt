package com.sharkaboi.mediahub.data.wrappers

class MHTaskState<T> private constructor() {
    private var internalDataHolder: T? = null
    var isSuccess: Boolean = false
        private set
    var error: MHError = MHError.EmptyError
        private set

    constructor(
        isSuccess: Boolean,
        data: T?,
        error: MHError
    ) : this() {
        this.isSuccess = isSuccess
        this.internalDataHolder = data
        this.error = error
    }

    val data: T
        get() {
            if (!isSuccess) {
                throw IllegalStateException("data() was called with isSuccess false. State - $this")
            }

            return internalDataHolder
                ?: throw IllegalStateException("data() was called with data null. State - $this")
        }
}
