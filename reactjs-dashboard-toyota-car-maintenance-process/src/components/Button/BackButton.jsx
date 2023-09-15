import React from 'react'
import { Button } from '@mui/material'

import ArrowBackIcon from '@mui/icons-material/ArrowBack'

const BackButton = ({ text, onClick }) => {
  return (
    <Button
      variant="contained"
      type="button"
      color="secondary"
      onClick={onClick}
      sx={{
        backgroundColor: '#ffb100',
        color: '#fff',
        fontSize: '1rem',
        ml: 1,
        mt: 1,
        '&:hover': {
          backgroundColor: '#ffc748',
        },
      }}
    >
      <ArrowBackIcon sx={{ fontSize: '24px' }} />
      {text ? text : 'Quay lại'}
    </Button>
  )
}

export default BackButton
