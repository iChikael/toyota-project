import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { HTTP_STATUS } from 'constants/global'
import dashboardApi from 'api/dashboardApi'

const namespace = 'dashboard'

const initialState = {
  data: {},
  status: HTTP_STATUS.IDLE,
  errorMessage: null,
  errorStatus: null,
}

export const getInfoDashboard = createAsyncThunk(
  `${namespace}/getInfoDashboard`,
  async (obj, { rejectWithValue }) => {
    return await dashboardApi
      .getInfoDashboard()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

const dashboardSlice = createSlice({
  name: namespace,
  initialState,
  reducers: {},
  extraReducers(builder) {
    builder
      .addCase(getInfoDashboard.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getInfoDashboard.fulfilled, (state, { payload }) => {
        state.data = payload
      })
      .addCase(getInfoDashboard.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
  },
})

const { reducer } = dashboardSlice

export default reducer
