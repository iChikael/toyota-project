import jwtDecode from 'jwt-decode'
import { useDispatch } from 'react-redux'
import { useLocation, Navigate, Outlet } from 'react-router-dom'

import { setAuth } from 'app/authSlice'
import { AUTH } from 'constants/global'

const RequireAuth = ({ allowedRoles }) => {
  const location = useLocation()

  const dispatch = useDispatch()

  const accessToken = localStorage.getItem(AUTH.ACCESS_TOKEN)

  let isAuth = false
  let tokenDecode = null
  let haveTime = false

  if (accessToken) {
    try {
      tokenDecode = jwtDecode(accessToken)
      const exp = new Date(tokenDecode.exp * 1000)
      const timeNow = new Date()

      if (exp > timeNow) {
        isAuth = true
        haveTime = true
        const action = setAuth(tokenDecode)
        dispatch(action)
      } else {
        localStorage.removeItem(AUTH.ACCESS_TOKEN)
      }
    } catch (error) {
      // console.log(error)
    }
  }

  return isAuth && allowedRoles.find((role) => tokenDecode?.role === role) ? (
    <Outlet />
  ) : tokenDecode?.fullName && haveTime ? (
    <Navigate to="/unauthorized" state={{ from: location }} replace />
  ) : (
    <Navigate to="/login" state={{ from: location }} replace />
  )
}

export default RequireAuth
