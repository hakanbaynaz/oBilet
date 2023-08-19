package com.example.obiletcasestudy.data.remote

/**
 * Single instance for session
 */
object Session {

    private lateinit var mSessionModel: SessionResponseModel.SessionModel

    /**
     * Storing session to later use
     */
    fun setSessionModel(sessionModel: SessionResponseModel.SessionModel) {
        mSessionModel = sessionModel
    }

    fun getSessionModel() = mSessionModel
}