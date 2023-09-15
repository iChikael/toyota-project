import React, { useState } from 'react'

import {
  Avatar,
  Box,
  Button,
  Checkbox,
  Container,
  CssBaseline,
  FormControlLabel,
  Grid,
  Link,
  Paper,
  TextField,
  Typography,
} from '@mui/material'

import { styled } from '@mui/material/styles'

import jwtDecode from 'jwt-decode'

// import { LockOutlined } from '@mui/lab'

import { LockOutlined } from '@mui/icons-material'

// import { makeStyles } from '@mui/material/styles'
// import { makeStyles } from '@mui/styles';

import { login } from 'app/authSlice'

import Images from 'constants/images'
import { useDispatch } from 'react-redux'
import { AUTH, ROLES } from 'constants/global'
import { useLocation, useNavigate } from 'react-router-dom'
import { ToastContainer } from 'react-toastify'
import ToastUtils from 'utils/ToastUtils'

const StyledRoot = styled('div')(({ theme }) => ({
  height: '100vh',
  backgroundImage: `url(${Images.BACKGOUND_LOGIN})`,
  backgroundRepeat: 'no-repeat',
  backgroundPosition: 'center',
  backgroundSize: 'cover',
  backgroundColor:
    theme.palette.type === 'light'
      ? theme.palette.grey[50]
      : theme.palette.grey[900],

  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',
  textAlign: 'center',
  justifyContent: 'center',
  // color: 'red',
  color: 'yellow',
  // maxWidth: 480,
  margin: 'auto',
  minHeight: '100vh',
  // padding: theme.spacing(12, 0),
  padding: '25px',
}))

const StyledForm = styled('div')(({ theme }) => ({
  // display: 'flex',
  // flexDirection: 'column',
  // alignItems: 'center',
  // textAlign: 'center',
  // justifyContent: 'center',
  // backgroundColor: '#fff',
  width: '100%', // Fix IE 11 issue.
  // margin: theme.spacing(4),
  // margin: 'auto',
}))

const StyledContent = styled('div')(({ theme }) => ({
  size: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
  },
  paper: {
    margin: theme.spacing(2, 6),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
}))

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'Copyright © '}
      <Link
        color="inherit"
        href="https://codegym.vn/trung-tam-codegym-hue/"
        target="_blank"
      >
        Relax Team
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  )
}

const LoginPage = () => {
  // const classes = StyledContent()

  const navigate = useNavigate()
  const location = useLocation()
  const from = location.state?.from?.pathname || '/'

  const dispatch = useDispatch()

  const [account, setAccount] = useState({
    username: '',
    password: '',
  })

  const setUsername = (e) => {
    const username = e.target.value
    setAccount({ ...account, username })
  }

  const setPassword = (e) => {
    const password = e.target.value
    setAccount({ ...account, password })
  }

  const handelLogin = () => {
    const action = login(account)

    dispatch(action)
      .unwrap()
      .then((data) => {
        const accessToken = data.token

        const token = jwtDecode(accessToken)
        const userRole = token.role

        localStorage.setItem(AUTH.ACCESS_TOKEN, accessToken)
        ToastUtils.showToastSuccessMessage('Đăng nhập thành công!')
        setTimeout(() => {
          if (userRole === ROLES.ADMIN) {
            navigate('/', { replace: true })
          } else if (userRole === ROLES.RECEPTION) {
            navigate('/reception/car-plates', { replace: true })
          } else if (userRole === ROLES.TECHNICAL) {
            navigate('/technical/order-services', { replace: true })
          } else if (userRole === ROLES.CASHIER) {
            navigate('/cashier/bill-services', { replace: true })
          }
        }, 2000)
      })
      .catch((error) => {
        ToastUtils.showToastFailMessage('Đăng nhập thất bại!')
        if (error.code === 'ERR_NETWORK') {
          ToastUtils.showToastFailMessage(
            'Lỗi khi tải dữ liệu doanh thu gần đây, vui lòng kiểm tra mạng'
          )
        }
        console.log(error)
        if (error.response) {
          const { username, password } = error.response.data
          // setErrors({
          //   ...errors,
          //   usernameErr: username,
          //   passwordErr: password,
          //   err: error.response.data.message,
          // })
        } else {
          // setErrors({
          //   ...errors,
          //   err: 'Lỗi hệ thống, vui lòng liên hệ admin !',
          // })
        }
        // setLoading(false)
      })
  }

  return (
    <>
      <StyledRoot>
        <Container
          maxWidth="100%"
          sx={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            textAlign: 'center',
            justifyContent: 'center',
          }}
        >
          <ToastContainer />
          <Grid component="main" margin="25px">
            <CssBaseline />
            {/* <Grid item xs={false} sm={4} md={7} className={classes.image} /> */}
            <Grid
              item
              xs={12}
              sm={8}
              md={5}
              component={Paper}
              elevation={1}
              square
            >
              <div
                style={{
                  margin: '16px 48px',
                  display: 'flex',
                  alignItems: 'center',
                  flexDirection: 'column',
                }}
              >
                <Avatar sx={{ marginTop: '25px' }}>
                  <LockOutlined />
                </Avatar>
                <Typography component="h1" variant="h5">
                  Sign in
                </Typography>
                <StyledForm>
                  <form noValidate>
                    <TextField
                      // onChange={(event) => setUsername(event)}
                      onChange={setUsername}
                      variant="outlined"
                      margin="normal"
                      // required
                      fullWidth
                      id="username"
                      label="Username"
                      name="username"
                      // autoFocus
                    />
                    <TextField
                      onChange={(event) => setPassword(event)}
                      variant="outlined"
                      margin="normal"
                      // required
                      fullWidth
                      name="password"
                      label="Password"
                      type="password"
                      id="password"
                      autoComplete="current-password"
                    />
                    <FormControlLabel
                      control={<Checkbox value="remember" color="primary" />}
                      label="Remember me"
                    />
                    <Button
                      type="button"
                      fullWidth
                      variant="contained"
                      color="primary"
                      onClick={handelLogin}
                    >
                      Sign In
                    </Button>
                    <Grid container>
                      <Grid item>
                        <Link href="#" variant="body2">
                          {"Don't have an account? Sign Up"}
                        </Link>
                      </Grid>
                    </Grid>
                    <Box mt={5} sx={{ marginBottom: '20px' }}>
                      <Copyright />
                    </Box>
                  </form>
                </StyledForm>
              </div>
            </Grid>
          </Grid>
        </Container>
      </StyledRoot>
    </>
  )
}

export default LoginPage
