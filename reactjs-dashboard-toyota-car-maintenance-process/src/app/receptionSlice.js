import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import { HTTP_STATUS } from 'constants/global'

import receptionApi from 'api/receptionApi'

const namespace = 'reception'

const initialState = {
  data: {
    carPlate: {},
    viewCarPlate: {},
    carPlateById: {},
    carPlates: [],
    carPlateSearchs: null,
    customers: [],
    cars: [],
    maintenances: [],
    maintenanceItems: [],
    maintenanceMaintenanceItems: [],
    repairItems: [],
    orderServices: [],
    orderServicePrev: null,
    carQueues: [],
    maintenanceItemAccessories: [],
    repairItemAccessories: [],
    amounts: [],
  },
  status: HTTP_STATUS.IDLE,
  errorMessage: null,
  errorStatus: null,
  formData: null,
}

export const searchCarPlate = createAsyncThunk(
  `${namespace}/searchCarPlate`,
  async ({ key }, { rejectWithValue }) => {
    return await receptionApi
      .searchCarPlate(key)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const searchCustomerByPhone = createAsyncThunk(
  `${namespace}/searchCustomerByPhone`,
  async ({ phone }, { rejectWithValue }) => {
    return await receptionApi
      .searchCustomerByPhone(phone)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const searchCarByName = createAsyncThunk(
  `${namespace}/searchCarByName`,
  async ({ name }, { rejectWithValue }) => {
    return await receptionApi
      .searchCarByName(name)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllCarPlates = createAsyncThunk(
  `${namespace}/getAllCarPlates`,
  async (obj, { rejectWithValue }) => {
    return await receptionApi
      .getAllCarPlates()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllMaintenanceItem = createAsyncThunk(
  `${namespace}/getAllMaintenanceItem`,
  async (obj, { rejectWithValue }) => {
    return await receptionApi
      .getAllMaintenanceItem()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)
export const getAllMaintenances = createAsyncThunk(
  `${namespace}/getAllMaintenances`,
  async (obj, { rejectWithValue }) => {
    return await receptionApi
      .getAllMaintenances()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const createCarPlate = createAsyncThunk(
  `${namespace}/createCarPlate`,
  async (obj, { rejectWithValue }) => {
    return await receptionApi
      .createCarPlate(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getManagementCarPlateByCarPlate = createAsyncThunk(
  `${namespace}/getManagementCarPlateByCarPlate`,
  async ({ carPlate }, { rejectWithValue }) => {
    return await receptionApi
      .getManagementCarPlateByCarPlate(carPlate)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllCarQueues = createAsyncThunk(
  `${namespace}/getAllCarQueues`,
  async (obj, { rejectWithValue }) => {
    return await receptionApi
      .getAllCarQueues()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllMaintenanceMaintenanceItemsByMaintenanceId =
  createAsyncThunk(
    `${namespace}/getAllMaintenanceMaintenanceItemsByMaintenanceId`,
    async ({ maintenanceId }, { rejectWithValue }) => {
      return await receptionApi
        .getAllMaintenanceMaintenanceItemsByMaintenanceId(maintenanceId)
        .then((response) => {
          return response.data
        })
        .catch((error) => {
          return rejectWithValue(error)
        })
    }
  )

export const getAllMaintenanceItemAccessoryByMaintenanceItemId =
  createAsyncThunk(
    `${namespace}/getAllMaintenanceItemAccessoryByMaintenanceItemId`,
    async ({ maintenanceItemId }, { rejectWithValue }) => {
      return await receptionApi
        .getAllMaintenanceItemAccessoryByMaintenanceItemId(maintenanceItemId)
        .then((response) => {
          return response.data
        })
        .catch((error) => {
          return rejectWithValue(error)
        })
    }
  )

export const getAllRepairItemAccessoryByRepairItemId = createAsyncThunk(
  `${namespace}/getAllRepairItemAccessoryByRepairItemId`,
  async ({ repairItemId }, { rejectWithValue }) => {
    return await receptionApi
      .getAllRepairItemAccessoryByRepairItemId(repairItemId)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllRepairItems = createAsyncThunk(
  `${namespace}/getAllRepairItems`,
  async (obj, { rejectWithValue }) => {
    return await receptionApi
      .getAllRepairItems()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const createOrderService = createAsyncThunk(
  `${namespace}/createOrderService`,
  async (obj, { rejectWithValue }) => {
    return await receptionApi
      .createOrderService(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getCarPlateById = createAsyncThunk(
  `${namespace}/getCarPlateById`,
  async ({ id }, { rejectWithValue }) => {
    return await receptionApi
      .getCarPlateById(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

const receptionSlice = createSlice({
  name: namespace,
  initialState,
  reducers: {
    setOrderServicePrev: (state, { payload }) => {
      state.data.orderServicePrev = payload
    },
    setAmount: (state, { payload }) => {
      state.data.amounts.push(payload)
    },
    setMaintenanceAccessory: (state, { payload }) => {
      state.data.orderServicePrev.maintenance.accessories[
        payload.index
      ].accessory = payload.newObject
    },
    setRepairAccessory: (state, { payload }) => {
      state.data.orderServicePrev.repairs[payload.index].accessory =
        payload.newObject
    },
    setRepairItem: (state, { payload }) => {
      state.data.orderServicePrev.repairSelects[payload.index] =
        payload.newObject
    },
    setPaymentRepairItem: (state, { payload }) => {
      state.data.orderServicePrev.repairSelects[payload.index] =
        payload.newObject
    },
    setPaymentMaintenanceAccessory: (state, { payload }) => {
      state.data.orderServicePrev.maintenance.accessories[
        payload.index
      ].accessory = payload.newObject
    },
    setPaymentRepairAccessory: (state, { payload }) => {
      state.data.orderServicePrev.repairs[payload.index].accessory =
        payload.newObject
    },
    setUnitRepairItem: (state, { payload }) => {
      state.data.orderServicePrev.repairSelects[payload.index] =
        payload.newObject
    },
    setCarPlateSearchs: (state, { payload }) => {
      state.data.carPlateSearchs = payload
    },
    setViewCarPlate: (state, { payload }) => {
      state.data.viewCarPlate = payload
    },
  },
  extraReducers(builder) {
    builder
      .addCase(searchCarPlate.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(searchCarPlate.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.carPlateSearchs = payload
      })
      .addCase(searchCarPlate.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getAllCarPlates.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllCarPlates.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.carPlates = payload
      })

      .addCase(getAllCarPlates.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getManagementCarPlateByCarPlate.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        getManagementCarPlateByCarPlate.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.carPlate = payload
          if (payload) {
            state.data.viewCarPlate = {
              customerName: payload.customerName,
              phone: payload.phone,
              email: payload.email,
              address:
                payload.customer.customerLocation.address +
                ', ' +
                payload.customer.customerLocation.wardName +
                ', ' +
                payload.customer.customerLocation.districtName +
                ', ' +
                payload.customer.customerLocation.provinceName,
              carName: payload.carName,
              carPlate: payload.carPlate,
              dateBuyCar: payload.customer.createdAt,
            }
          }
        }
      )
      .addCase(
        getManagementCarPlateByCarPlate.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(searchCustomerByPhone.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(searchCustomerByPhone.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.customers = payload.data
      })
      .addCase(searchCustomerByPhone.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(searchCarByName.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(searchCarByName.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.cars = payload.data
      })
      .addCase(searchCarByName.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(createCarPlate.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(createCarPlate.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        // state.data.cars = payload.data
      })
      .addCase(createCarPlate.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getAllMaintenanceItem.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllMaintenanceItem.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.maintenanceItems = payload
      })
      .addCase(getAllMaintenanceItem.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getAllMaintenances.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllMaintenances.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.maintenances = payload
      })
      .addCase(getAllMaintenances.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(
        getAllMaintenanceMaintenanceItemsByMaintenanceId.pending,
        (state) => {
          state.status = HTTP_STATUS.PENDING
        }
      )
      .addCase(
        getAllMaintenanceMaintenanceItemsByMaintenanceId.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.maintenanceMaintenanceItems = payload
        }
      )
      .addCase(
        getAllMaintenanceMaintenanceItemsByMaintenanceId.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(getAllRepairItems.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllRepairItems.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.repairItems = payload
      })
      .addCase(getAllRepairItems.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(createOrderService.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(createOrderService.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.orderServices = payload
      })
      .addCase(createOrderService.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getAllCarQueues.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllCarQueues.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.carQueues = payload
      })
      .addCase(getAllCarQueues.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(
        getAllMaintenanceItemAccessoryByMaintenanceItemId.pending,
        (state) => {
          state.status = HTTP_STATUS.PENDING
        }
      )
      .addCase(
        getAllMaintenanceItemAccessoryByMaintenanceItemId.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.maintenanceItemAccessories = payload
        }
      )
      .addCase(
        getAllMaintenanceItemAccessoryByMaintenanceItemId.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(getAllRepairItemAccessoryByRepairItemId.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        getAllRepairItemAccessoryByRepairItemId.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.repairItemAccessories = payload
        }
      )
      .addCase(
        getAllRepairItemAccessoryByRepairItemId.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(getCarPlateById.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getCarPlateById.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.carPlateById = payload
      })
      .addCase(getCarPlateById.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
  },
})

const { reducer, actions } = receptionSlice
export const {
  setOrderServicePrev,
  setAmount,
  setMaintenanceAccessory,
  setRepairAccessory,
  setRepairItem,
  setPaymentRepairItem,
  setPaymentMaintenanceAccessory,
  setPaymentRepairAccessory,
  setUnitRepairItem,
  setCarPlateSearchs,
  setViewCarPlate,
} = actions

export default reducer
