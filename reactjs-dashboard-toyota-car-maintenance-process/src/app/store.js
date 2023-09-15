import { configureStore } from '@reduxjs/toolkit'
import authReducer from './authSlice'
import staffReducer from './staffSlice'
import locationReducer from './locationRegionSlice'
import accessoryReducer from './accessoriesSlice'
import carQueueReducer from './carQueueSlice'
import repairItemReducer from './repairItemSlice'
import serviceAreaReducer from './serviceAreaSlice'
import carReducer from './carSlice'
import receptionReducer from './receptionSlice'
import technicalReducer from './technicalSlice'
import cashierReducer from './cashierSlice'
import customerReducer from './customerSlice'
import dashboardReducer from './dashboardSlice'

const rootReducer = {
  auth: authReducer,
  staff: staffReducer,
  customer: customerReducer,
  locationRegion: locationReducer,
  accessory: accessoryReducer,
  carQueue: carQueueReducer,
  repairItem: repairItemReducer,
  serviceArea: serviceAreaReducer,
  car: carReducer,
  reception: receptionReducer,
  technical: technicalReducer,
  cashier: cashierReducer,
  dashboard: dashboardReducer,
}

const store = configureStore({
  reducer: rootReducer,
})

export default store
