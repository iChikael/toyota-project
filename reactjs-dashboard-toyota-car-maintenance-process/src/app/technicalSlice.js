import { HTTP_STATUS } from 'constants/global'
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import technicalApi from 'api/technicalApi'

const namespace = 'orderService'

const initialState = {
  data: {
    orderServices: [],
    orderService: {},
    orderServicesByServiceArea: [],

    orderMaintenanceByOrderServiceId: {},
    orderMaintenanceItemsByOrderServiceId: [],
    orderMaintenanceItemAccessoriesByOrderMaintenanceItemId: [],
    orderRepairItemsByOrderServiceId: [],
    orderRepairItemAccessoriesByOrderRepairItemId: [],
    viewOrderServices: [],
    viewOrderServicesByServiceArea: [],

    serviceItemsUseGeneralRepairArea: [],
    accessoryByServiceItems: [],
    accessories: [],

    maintenanceItemsByServiceArea: [],
    repairItemsByServiceArea: [],
    orderMaintenanceItemsByCurrentServiceArea: [],
    viewOrderMaintenanceItemsByCurrentServiceArea: [],
    orderRepairItemsByCurrentServiceArea: [],
    viewOrderRepairItemsByCurrentServiceArea: [],
  },
  status: HTTP_STATUS.IDLE,
  errorMessage: null,
  errorStatus: null,
}

export const getAllOrderServices = createAsyncThunk(
  `${namespace}/getAllOrderServices`,
  async (obj, { rejectWithValue }) => {
    return await technicalApi
      .getAllOrderServices()
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getOrderServiceById = createAsyncThunk(
  `${namespace}/getOrderServiceById`,
  async ({ id }, { rejectWithValue }) => {
    return await technicalApi
      .getOrderServiceById(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllOrderServiceByCurrentServiceArea = createAsyncThunk(
  `${namespace}/getAllOrderServiceByCurrentServiceArea`,
  async ({ serviceAreaId }, { rejectWithValue }) => {
    return await technicalApi
      .getAllOrderServiceByCurrentServiceArea(serviceAreaId)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllOrderMaintenancesByOrderServiceId = createAsyncThunk(
  `${namespace}/getAllOrderMaintenancesByOrderServiceId`,
  async ({ id }, { rejectWithValue }) => {
    return await technicalApi
      .getAllOrderMaintenancesByOrderServiceId(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllOrderMaintenanceItemsByOrderServiceId = createAsyncThunk(
  `${namespace}/getAllOrderMaintenanceItemsByOrderServiceId`,
  async ({ id }, { rejectWithValue }) => {
    return await technicalApi
      .getAllOrderMaintenanceItemsByOrderServiceId(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllOrderMaintenanceItemsByCurrentServiceArea = createAsyncThunk(
  `${namespace}/getAllOrderMaintenanceItemsByCurrentServiceArea`,
  async (obj, { rejectWithValue }) => {
    return await technicalApi
      .getAllOrderMaintenanceItemsByCurrentServiceArea(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)
export const getAllOrderRepairItemsByCurrentServiceArea = createAsyncThunk(
  `${namespace}/getAllOrderRepairItemsByCurrentServiceArea`,
  async (obj, { rejectWithValue }) => {
    return await technicalApi
      .getAllOrderRepairItemsByCurrentServiceArea(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllOrderMaintenanceItemAccessoriesByOrderMaintenanceItemId =
  createAsyncThunk(
    `${namespace}/getAllOrderMaintenanceItemAccessoriesByOrderMaintenanceItemId`,
    async ({ id }, { rejectWithValue }) => {
      return await technicalApi
        .getAllOrderMaintenanceItemAccessoriesByOrderMaintenanceItemId(id)
        .then((response) => {
          return response.data
        })
        .catch((error) => {
          return rejectWithValue(error)
        })
    }
  )

export const getAllOrderRepairItemsByOrderServiceId = createAsyncThunk(
  `${namespace}/getAllOrderRepairItemsByOrderServiceId`,
  async ({ id }, { rejectWithValue }) => {
    return await technicalApi
      .getAllOrderRepairItemsByOrderServiceId(id)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const getAllOrderRepairItemAccessoriesByOrderRepairItemId =
  createAsyncThunk(
    `${namespace}/getAllOrderRepairItemAccessoriesByOrderRepairItemId`,
    async ({ id }, { rejectWithValue }) => {
      return await technicalApi
        .getAllOrderRepairItemAccessoriesByOrderRepairItemId(id)
        .then((response) => {
          return response.data
        })
        .catch((error) => {
          return rejectWithValue(error)
        })
    }
  )

export const updateStatusMaintenanceItemByServiceArea = createAsyncThunk(
  `${namespace}/updateStatusMaintenanceItemByServiceArea`,
  async (obj, { rejectWithValue }) => {
    return await technicalApi
      .updateStatusMaintenanceItemByServiceArea(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)
export const updateStatusRepairItemByServiceArea = createAsyncThunk(
  `${namespace}/updateStatusRepairItemByServiceArea`,
  async (obj, { rejectWithValue }) => {
    return await technicalApi
      .updateStatusRepairItemByServiceArea(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)
export const updateStatusMaintenanceItemByServiceAreaDone = createAsyncThunk(
  `${namespace}/updateStatusMaintenanceItemByServiceAreaDone`,
  async (obj, { rejectWithValue }) => {
    return await technicalApi
      .updateStatusMaintenanceItemByServiceAreaDone(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)
export const updateStatusRepairItemByServiceAreaDone = createAsyncThunk(
  `${namespace}/updateStatusRepairItemByServiceAreaDone`,
  async (obj, { rejectWithValue }) => {
    return await technicalApi
      .updateStatusRepairItemByServiceAreaDone(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const updateCurrentServiceAreaOrderService = createAsyncThunk(
  `${namespace}/updateCurrentServiceAreaOrderService`,
  async (obj, { rejectWithValue }) => {
    return await technicalApi
      .updateCurrentServiceAreaOrderService(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const updateStatusCarQueueByOrderService = createAsyncThunk(
  `${namespace}/updateStatusCarQueueByOrderService`,
  async (obj, { rejectWithValue }) => {
    return await technicalApi
      .updateStatusCarQueueByOrderService(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const updateStatusOrderMaintenanceByOrderServiceId = createAsyncThunk(
  `${namespace}/updateStatusOrderMaintenanceByOrderServiceId`,
  async (obj, { rejectWithValue }) => {
    return await technicalApi
      .updateStatusOrderMaintenanceByOrderServiceId(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

export const updateStatusMaintenanceItemByServiceAreaDoneAll = createAsyncThunk(
  `${namespace}/updateStatusMaintenanceItemByServiceAreaDoneAll`,
  async (obj, { rejectWithValue }) => {
    return await technicalApi
      .updateStatusMaintenanceItemByServiceAreaDoneAll(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)
export const updateStatusRepairItemByServiceAreaDoneAll = createAsyncThunk(
  `${namespace}/updateStatusRepairItemByServiceAreaDoneAll`,
  async (obj, { rejectWithValue }) => {
    return await technicalApi
      .updateStatusRepairItemByServiceAreaDoneAll(obj)
      .then((response) => {
        return response.data
      })
      .catch((error) => {
        return rejectWithValue(error)
      })
  }
)

const technicalSlice = createSlice({
  name: namespace,
  initialState,
  reducers: {
    setServiceItemsUseGeneralRepairArea: (state, { payload }) => {
      state.data.serviceItemsUseGeneralRepairArea = payload
    },
    getAccessoriesByOrderServiceItemId: (state, { payload }) => {
      state.data.orderMaintenanceItemsByCurrentServiceArea.filter((item) => {
        if (item.id === payload.id) {
          state.data.accessoryByServiceItems = item
        }
      })
      state.data.orderRepairItemsByCurrentServiceArea.filter((item) => {
        if (item.id === payload.id) {
          state.data.accessoryByServiceItems = item
        }
      })
    },
    setOrderServices: (state, { payload }) => {
      state.status = HTTP_STATUS.FULFILLED
      state.data.orderServices = payload
      const viewOrderServicesNew = []
      if (payload?.length) {
        payload.map((item) => {
          viewOrderServicesNew.push({
            id: item.id,
            customerName: item.carQueue.fullName,
            carPlate: item.carQueue.carNumberPlates,
            carName: item.car.title,
            carVehicle: item.car.vehicle.name,
            customerReq: item.customerReq,
            doEarly: item.doEarly,
            timeMinute: item.timeMinute,
            status: item.carQueue.status,
            currentServiceArea: item.currentServiceArea,
          })
        })
      }

      state.data.viewOrderServices = viewOrderServicesNew
    },
  },
  extraReducers(builder) {
    builder
      .addCase(getAllOrderServices.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getAllOrderServices.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.orderServices = payload
        const viewOrderServicesNew = []
        if (payload?.length) {
          payload.map((item) => {
            viewOrderServicesNew.push({
              id: item.id,
              customerName: item.carQueue.fullName,
              carPlate: item.carQueue.carNumberPlates,
              carName: item.car.title,
              carVehicle: item.car.vehicle.name,
              customerReq: item.customerReq,
              doEarly: item.doEarly,
              timeMinute: item.timeMinute,
              status: item.carQueue.status,
              currentServiceArea: item.currentServiceArea,
            })
          })
        }

        state.data.viewOrderServices = viewOrderServicesNew
      })
      .addCase(getAllOrderServices.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getOrderServiceById.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(getOrderServiceById.fulfilled, (state, { payload }) => {
        state.status = HTTP_STATUS.FULFILLED
        state.data.orderService = payload
      })
      .addCase(getOrderServiceById.rejected, (state, { payload }) => {
        state.status = HTTP_STATUS.REJECTED
        if (payload.response) {
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      })
      .addCase(getAllOrderServiceByCurrentServiceArea.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        getAllOrderServiceByCurrentServiceArea.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.orderServicesByServiceArea = payload
          const viewOrderServicesByServiceAreaNew = []
          if (payload?.length) {
            payload.map((item) => {
              viewOrderServicesByServiceAreaNew.push({
                id: item.id,
                customerName: item.carQueue.fullName,
                carPlate: item.carQueue.carNumberPlates,
                carName: item.car.title,
                carVehicle: item.car.vehicle.name,
                customerReq: item.customerReq,
                doEarly: item.doEarly,
                timeMinute: item.timeMinute,
                status: item.carQueue.status,
                currentServiceArea: item.currentServiceArea,
              })
            })
          }

          state.data.viewOrderServicesByServiceArea =
            viewOrderServicesByServiceAreaNew
        }
      )
      .addCase(
        getAllOrderServiceByCurrentServiceArea.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(getAllOrderMaintenancesByOrderServiceId.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        getAllOrderMaintenancesByOrderServiceId.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.orderMaintenanceByOrderServiceId = payload
        }
      )
      .addCase(
        getAllOrderMaintenancesByOrderServiceId.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(getAllOrderMaintenanceItemsByOrderServiceId.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        getAllOrderMaintenanceItemsByOrderServiceId.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.orderMaintenanceItemsByOrderServiceId = payload
        }
      )
      .addCase(
        getAllOrderMaintenanceItemsByOrderServiceId.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(
        getAllOrderMaintenanceItemsByCurrentServiceArea.pending,
        (state) => {
          state.status = HTTP_STATUS.PENDING
        }
      )
      .addCase(
        getAllOrderMaintenanceItemsByCurrentServiceArea.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.orderMaintenanceItemsByCurrentServiceArea = []
          state.data.orderMaintenanceItemsByCurrentServiceArea = payload || []
          const viewOrderMaintenanceItemsByCurrentServiceAreaNew = []
          if (payload) {
            if (payload.length) {
              payload.map((item) => {
                viewOrderMaintenanceItemsByCurrentServiceAreaNew.push({
                  id: 'BDĐK-' + item.id,
                  name: item.serviceItemName,
                  job: item.checklistName,
                  status: item.status,
                })
              })
            }
          }

          state.data.viewOrderMaintenanceItemsByCurrentServiceArea =
            viewOrderMaintenanceItemsByCurrentServiceAreaNew
        }
      )
      .addCase(
        getAllOrderMaintenanceItemsByCurrentServiceArea.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(
        getAllOrderMaintenanceItemAccessoriesByOrderMaintenanceItemId.pending,
        (state) => {
          state.status = HTTP_STATUS.PENDING
        }
      )
      .addCase(
        getAllOrderMaintenanceItemAccessoriesByOrderMaintenanceItemId.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.orderMaintenanceItemAccessoriesByOrderMaintenanceItemId =
            payload
        }
      )
      .addCase(
        getAllOrderMaintenanceItemAccessoriesByOrderMaintenanceItemId.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(getAllOrderRepairItemsByOrderServiceId.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        getAllOrderRepairItemsByOrderServiceId.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.orderRepairItemsByOrderServiceId = payload
        }
      )
      .addCase(
        getAllOrderRepairItemsByOrderServiceId.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(getAllOrderRepairItemsByCurrentServiceArea.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        getAllOrderRepairItemsByCurrentServiceArea.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.orderRepairItemsByCurrentServiceArea = payload || []
          const viewOrderRepairItemsByCurrentServiceAreaNew = []
          if (payload) {
            if (payload.length) {
              payload.map((item) => {
                viewOrderRepairItemsByCurrentServiceAreaNew.push({
                  id: 'GMGĐ-' + item.id,
                  name: item.name,
                  job: 'Thay thế, sửa chữa',
                  status: item.status,
                })
              })
            }
          }
          state.data.viewOrderRepairItemsByCurrentServiceArea =
            viewOrderRepairItemsByCurrentServiceAreaNew
        }
      )
      .addCase(
        getAllOrderRepairItemsByCurrentServiceArea.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(
        getAllOrderRepairItemAccessoriesByOrderRepairItemId.pending,
        (state) => {
          state.status = HTTP_STATUS.PENDING
        }
      )
      .addCase(
        getAllOrderRepairItemAccessoriesByOrderRepairItemId.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.orderRepairItemAccessoriesByOrderRepairItemId = payload
        }
      )
      .addCase(
        getAllOrderRepairItemAccessoriesByOrderRepairItemId.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(updateStatusMaintenanceItemByServiceArea.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        updateStatusMaintenanceItemByServiceArea.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.maintenanceItemsByServiceArea = payload
        }
      )
      .addCase(
        updateStatusMaintenanceItemByServiceArea.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(updateStatusRepairItemByServiceArea.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        updateStatusRepairItemByServiceArea.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.repairItemsByServiceArea = payload
        }
      )
      .addCase(
        updateStatusRepairItemByServiceArea.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
      .addCase(
        updateStatusMaintenanceItemByServiceAreaDone.pending,
        (state) => {
          state.status = HTTP_STATUS.PENDING
        }
      )
      .addCase(
        updateStatusMaintenanceItemByServiceAreaDone.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.orderMaintenanceItemsByCurrentServiceArea = payload || []
          const viewOrderMaintenanceItemsByCurrentServiceAreaNew = []
          if (payload) {
            if (payload.length) {
              payload.map((item) => {
                viewOrderMaintenanceItemsByCurrentServiceAreaNew.push({
                  id: 'BDĐK-' + item.id,
                  name: item.serviceItemName,
                  job: item.checklistName,
                  status: item.status,
                })
              })
            }
          }

          state.data.viewOrderMaintenanceItemsByCurrentServiceArea =
            viewOrderMaintenanceItemsByCurrentServiceAreaNew
        }
      )
      .addCase(
        updateStatusMaintenanceItemByServiceAreaDone.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      )
      .addCase(updateStatusRepairItemByServiceAreaDone.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        updateStatusRepairItemByServiceAreaDone.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.orderRepairItemsByCurrentServiceArea = payload || []
          const viewOrderRepairItemsByCurrentServiceAreaNew = []
          if (payload) {
            if (payload.length) {
              payload.map((item) => {
                viewOrderRepairItemsByCurrentServiceAreaNew.push({
                  id: 'GMGĐ-' + item.id,
                  name: item.name,
                  job: 'Thay thế, sửa chữa',
                  status: item.status,
                })
              })
            }
          }
          state.data.viewOrderRepairItemsByCurrentServiceArea =
            viewOrderRepairItemsByCurrentServiceAreaNew
        }
      )
      .addCase(
        updateStatusRepairItemByServiceAreaDone.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      )
      .addCase(updateCurrentServiceAreaOrderService.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        updateCurrentServiceAreaOrderService.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.orderService = payload
        }
      )
      .addCase(
        updateCurrentServiceAreaOrderService.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      )
      .addCase(updateStatusCarQueueByOrderService.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        updateStatusCarQueueByOrderService.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          // state.data.orderService = payload
        }
      )
      .addCase(
        updateStatusCarQueueByOrderService.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      )
      .addCase(
        updateStatusOrderMaintenanceByOrderServiceId.pending,
        (state) => {
          state.status = HTTP_STATUS.PENDING
        }
      )
      .addCase(
        updateStatusOrderMaintenanceByOrderServiceId.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          // state.data.orderService = payload
        }
      )
      .addCase(
        updateStatusOrderMaintenanceByOrderServiceId.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      )
      .addCase(
        updateStatusMaintenanceItemByServiceAreaDoneAll.pending,
        (state) => {
          state.status = HTTP_STATUS.PENDING
        }
      )
      .addCase(
        updateStatusMaintenanceItemByServiceAreaDoneAll.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.orderMaintenanceItemsByCurrentServiceArea = payload || []
          const viewOrderMaintenanceItemsByCurrentServiceAreaNew = []
          if (payload) {
            if (payload.length) {
              payload.map((item) => {
                viewOrderMaintenanceItemsByCurrentServiceAreaNew.push({
                  id: 'BDĐK-' + item.id,
                  name: item.serviceItemName,
                  job: item.checklistName,
                  status: item.status,
                })
              })
            }
          }

          state.data.viewOrderMaintenanceItemsByCurrentServiceArea =
            viewOrderMaintenanceItemsByCurrentServiceAreaNew
        }
      )
      .addCase(
        updateStatusMaintenanceItemByServiceAreaDoneAll.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          state.errorMessage = payload.response.statusText
          state.errorStatus = payload.response.status
        }
      )
      .addCase(updateStatusRepairItemByServiceAreaDoneAll.pending, (state) => {
        state.status = HTTP_STATUS.PENDING
      })
      .addCase(
        updateStatusRepairItemByServiceAreaDoneAll.fulfilled,
        (state, { payload }) => {
          state.status = HTTP_STATUS.FULFILLED
          state.data.orderRepairItemsByCurrentServiceArea = payload || []
          const viewOrderRepairItemsByCurrentServiceAreaNew = []
          if (payload) {
            if (payload.length) {
              payload.map((item) => {
                viewOrderRepairItemsByCurrentServiceAreaNew.push({
                  id: 'GMGĐ-' + item.id,
                  name: item.name,
                  job: 'Thay thế, sửa chữa',
                  status: item.status,
                })
              })
            }
          }
          state.data.viewOrderRepairItemsByCurrentServiceArea =
            viewOrderRepairItemsByCurrentServiceAreaNew
        }
      )
      .addCase(
        updateStatusRepairItemByServiceAreaDoneAll.rejected,
        (state, { payload }) => {
          state.status = HTTP_STATUS.REJECTED
          if (payload.response) {
            state.errorMessage = payload.response.statusText
            state.errorStatus = payload.response.status
          }
        }
      )
  },
})

const { reducer, actions } = technicalSlice
export const {
  setServiceItemsUseGeneralRepairArea,
  getAccessoriesByOrderServiceItemId,
  setOrderServices,
} = actions

export default reducer
