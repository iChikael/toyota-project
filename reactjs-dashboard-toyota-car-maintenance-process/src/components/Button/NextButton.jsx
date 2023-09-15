import React from 'react'
import { Button } from '@mui/material'

import ArrowRightIcon from '@mui/icons-material/ArrowRight'

const NextButton = ({ text, onClick }) => {
  return (
    <Button
      variant="contained"
      type="button"
      color="secondary"
      onClick={onClick}
      sx={{
        backgroundColor: '#1c7dff',
        color: '#fff',
        fontSize: '1rem',
        mr: 1,
        mt: 1,
        '&:hover': {
          backgroundColor: '#76b1ff',
        },
      }}
    >
      {text ? text : 'Kế tiếp'}
      <ArrowRightIcon sx={{ fontSize: '24px' }} />
    </Button>
  )
}

export default NextButton
