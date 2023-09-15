import { HTTP_STATUS } from 'constants/global'
import vehicleApi from 'api/vehicleApi'
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'

const namespace = 'car'

const initialState = {
  data: {
    vehicles: [{}],
    vehicle: {},
  },
  status: HTTP_STATUS.IDLE,
  errorMessage: null,
  errorStatus: null,
}

export const getAllVehicles = createAsyncThunk(
  `${namespace}/getAllVehicles`,
  async (obj, { rejectWithValue }) => {
    return await vehicleApi
      .getAllVehicles()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getVehicleById = createAsyncThunk(
  `${namespace}/getVehicleById`,
  async (obj, { rejectWithValue }) => {
    return await vehicleApi
      .getVehicleByid()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const deleteVehicleById = createAsyncThunk(
  `${namespace}/deleteVehicleById`,
  async ({ id }, { rejectWithValue }) => {
    try {
      await vehicleApi.delete(id)
      return null
    } catch (error) {
      return rejectWithValue(error.response.data)
    }
  }
)

export const createVehicle = createAsyncThunk(
  `${namespace}/createVehicle`,
  async (obj, { rejectWithValue }) => {
    try {
      await vehicleApi.createVehicle(obj).then((response) => {
        return response.data
      })
    } catch (error) {
      return rejectWithValue(error.response.data)
    }
  }
)

const vehicleSlice = createSlice({
  name: namespace,
  initialState,
  reducers: {},
  extraReducers(builder) {
    builder
      .addCase(getAllVehicles.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllVehicles.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.vehicles = payload
      })
      .addCase(createVehicle.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(createVehicle.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.vehicle = payload
      })
      .addCase(createVehicle.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
  },
})

const { reducer } = vehicleSlice

export default reducer
