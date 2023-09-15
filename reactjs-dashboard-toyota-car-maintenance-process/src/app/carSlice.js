import { HTTP_STATUS } from 'constants/global'
import carApi from 'api/carApi'
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import Helper from 'utils/Helper'

const namespace = 'cars'

const initialState = {
  data: {
    cars: [{}],
    car: {},
  },
  status: HTTP_STATUS.IDLE,
  errorMessage: null,
  errorStatus: null,
}

export const getAllCars = createAsyncThunk(
  `${namespace}/getAllCars`,
  async (obj, { rejectWithValue }) => {
    return await carApi
      .getAllCars()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getCarById = createAsyncThunk(
  `${namespace}/getCarById`,
  async (obj, { rejectWithValue }) => {
    return await carApi
      .getCarByid(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const deleteCarById = createAsyncThunk(
  `${namespace}/deleteCarById`,
  async (id, { rejectWithValue }) => {
    return carApi
      .deleteCarById(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error.response.data)
      })
  }
)

export const createCar = createAsyncThunk(
  `${namespace}/createCar`,
  async (obj, { rejectWithValue }) => {
    try {
      await carApi.createCar(obj).then((response) => {
        return response.data
      })
    } catch (error) {
      return rejectWithValue(error.response.data)
    }
  }
)

const carSlice = createSlice({
  name: namespace,
  initialState,
  reducers: {},
  extraReducers(builder) {
    builder
      .addCase(getAllCars.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllCars.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.cars = payload
        state.data.cars.forEach((item) => {
          item.price = Helper.formatCurrencyToVND(item.price)
        })
      })
      .addCase(createCar.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(createCar.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.car = payload
      })
      .addCase(createCar.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getCarById.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.car = payload
      })
  },
})

const { reducer } = carSlice

export default reducer
