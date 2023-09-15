import React, { useState } from 'react'
import { FormControl } from '@mui/material'
import { Button } from '@mui/material'
import { FormHelperText } from '@mui/material'
import CloudUploadIcon from '@mui/icons-material/CloudUpload'

const ImageAfterInput = (props) => {
  const [fileName, setFileName] = useState('')

  const handleIdentificationImageAfterChange = (e) => {
    e.preventDefault()
    let reader = new FileReader()
    let file = e.target.files[0]
    if (file) {
      reader.onloadend = () => setFileName(file.name)
      reader.readAsDataURL(file)
      if (props.inputPurpose === 'before') {
        props.setFieldValue('identificationImageBefore', file)
      } else if (props.inputPurpose === 'after') {
        props.setFieldValue('identificationImageAfter', file)
        console.log('after')
      }
    }
    props.onChangeCustom && props.onChangeCustom(e)
  }

  const errorMessage = props.form.errors[props.field.name]

  return (
    <FormControl margin="normal">
      <input
        style={{ display: 'none' }}
        id="image-after-upload"
        name={props.field.name}
        type="file"
        accept="image/*"
        onChange={handleIdentificationImageAfterChange}
      />
      <label htmlFor="image-after-upload">
        <Button color="primary" margin="normal" component="span">
          <CloudUploadIcon />
        </Button>
      </label>
      {fileName ? (
        <FormHelperText id="image-after-upload-filename">{fileName}</FormHelperText>
      ) : null}
      {errorMessage ? (
        <FormHelperText id="image-after-upload-helper-text" error={true}>
          {errorMessage}
        </FormHelperText>
      ) : null}
    </FormControl>
  )
}

export default ImageAfterInput
