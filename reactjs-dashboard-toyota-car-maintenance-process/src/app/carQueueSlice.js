import { HTTP_STATUS } from 'constants/global'
import carQueueApi from 'api/carQueueApi'
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'

const namespace = 'carqueue'

const initialState = {
  data: {
    carQueues: [],
    carQueue: {},
  },
  status: HTTP_STATUS.IDLE,
  errorMessage: null,
  errorStatus: null,
}

export const getAllCarQueue = createAsyncThunk(
  `${namespace}/getAllCarQueue`,
  async (obj, { rejectWithValue }) => {
    return await carQueueApi
      .getAllCarQueue()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const updateCarQueueById = createAsyncThunk(
  `${namespace}/updateCarQueueById`,
  async (obj, { rejectWithValue }) => {
    return await carQueueApi
      .updateCarQueueById(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getCarQueueById = createAsyncThunk(
  `${namespace}/getCarQueueById`,
  async (id, { rejectWithValue }) => {
    return await carQueueApi
      .getCarQueueByid(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const deleteCarQueueById = createAsyncThunk(
  `${namespace}/deleteCarQueueById`,
  async (id, { rejectWithValue }) => {
    return await carQueueApi
      .deleteCarQueueById(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const createCarQueue = createAsyncThunk(
  `${namespace}/createCarQueue`,
  async (obj, { rejectWithValue }) => {
    return await carQueueApi
      .createCarQueue(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

const carQueueSlice = createSlice({
  name: namespace,
  initialState,
  reducers: {
    setCarQueues: (state, { payload }) => {
      state.data.carQueues[payload.index] = payload.newObject
    },
  },
  extraReducers(builder) {
    builder
      .addCase(getAllCarQueue.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllCarQueue.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.carQueues = payload
      })
      .addCase(getAllCarQueue.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(createCarQueue.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(createCarQueue.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.carQueue = payload
      })
      .addCase(createCarQueue.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getCarQueueById.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getCarQueueById.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.carQueue = payload
      })
      .addCase(getCarQueueById.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(updateCarQueueById.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(updateCarQueueById.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.carQueue = payload
      })
      .addCase(updateCarQueueById.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
  },
})

const { reducer, actions } = carQueueSlice

export const { setCarQueues } = actions

export default reducer
