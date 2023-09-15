const defaultNumbers = ' hai ba bốn năm sáu bảy tám chín'

const chuHangDonVi = ('1 một' + defaultNumbers).split(' ')
const chuHangChuc = ('lẻ mười' + defaultNumbers).split(' ')
const chuHangTram = ('không một' + defaultNumbers).split(' ')
const dvBlock = '1 nghìn triệu tỷ'.split(' ')

const convert_block_three = (number) => {
  if (number === '000') return ''
  let _a = number + ''
  switch (_a.length) {
    case 0:
      return ''
    case 1:
      return chuHangDonVi[_a]
    case 2:
      return convert_block_two(_a)
    case 3:
      let chuc_dv = ''
      if (_a.slice(1, 3) !== '00') {
        chuc_dv = convert_block_two(_a.slice(1, 3))
      }
      let tram = chuHangTram[_a[0]] + ' trăm'
      return tram + ' ' + chuc_dv
    default:
  }
}

const convert_block_two = (number) => {
  let dv = chuHangDonVi[number[1]]
  let chuc = chuHangChuc[number[0]]
  let append = ''
  if (number[0] > 0 && number[1] === 5) {
    dv = 'lăm'
  }
  if (number[0] > 1) {
    append = ' mươi'

    if (number[1] === 1) {
      dv = ' mốt'
    }
  }
  return chuc + '' + append + ' ' + dv
}

const Helper = {
  formatCurrencyToVND: (number) => {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(Number(number))
  },

  formatCurrency: (amount) => {
    let number = amount.toString()

    if (number.length <= 6) {
      return (
        number.substring(0, number.length - 3) +
        '.' +
        number.substring(number.length - 3, number.length)
      )
    }
    if (number.length <= 9 && number.length >= 7) {
      return (
        number.substring(0, number.length - 6) +
        '.' +
        number.substring(number.length - 6, number.length - 3) +
        '.' +
        number.substring(number.length - 3, number.length)
      )
    }
    if (number.length > 9) {
      return (
        number.substring(0, number.length - 3) +
        '.' +
        number.substring(number.length - 9, number.length - 6) +
        '.' +
        number.substring(number.length - 6, number.length - 3) +
        '.' +
        number.substring(number.length - 3, number.length)
      )
    }

    return amount
  },
  formatQuantity: (quantity) => {
    let number = quantity.toString()
    if (number.length === 1) {
      return number + ',00'
    }
    if (number.length === 3) {
      return number.substring(0, 1) + ',' + number.substring(2, 3) + '0'
    }
    if (number.length === 4) {
      return number.substring(0, 1) + ',' + number.substring(2, 4)
    }

    return quantity
  },
  getFilename: (fileUrl) => {
    return fileUrl.split('/').pop().split('.')[0]
  },
  countTime: (timeA, timeB) => {
    let result
    const c = parseInt((timeA - timeB) / (1000 * 60), 10)
    if (c <= 0) {
      result = 'vài giây trước'
    } else if (c < 60) {
      result = `${c} phút trước`
    } else {
      const d = parseInt((timeA - timeB) / (1000 * 60 * 60), 10)
      result = `${d} giờ trước`
    }
    return result
  },
  formatDate: (date) => {
    const yyyy = date.getFullYear()
    let mm = date.getMonth() + 1 // Months start at 0!
    let dd = date.getDate()

    if (dd < 10) dd = '0' + dd
    if (mm < 10) mm = '0' + mm

    const formattedToday = dd + '-' + mm + '-' + yyyy
    return formattedToday
  },
  formatPhoneNumber: (phone) => {
    return (
      phone.substring(0, 4) +
      ' ' +
      phone.substring(4, 7) +
      ' ' +
      phone.substring(7, phone.length)
    )
  },
  formatCarNumberPlate: (numberPlate) => {
    if (numberPlate.length <= 8) {
      return (
        numberPlate.substring(0, 3) +
        ' - ' +
        numberPlate.substring(3, 6) +
        '.' +
        numberPlate.substring(6, numberPlate.length)
      )
    }

    if (numberPlate.length === 9) {
      return (
        numberPlate.substring(0, 2) +
        ' - ' +
        numberPlate.substring(2, 4) +
        ' ' +
        numberPlate.substring(4, 7) +
        '.' +
        numberPlate.substring(7, numberPlate.length)
      )
    }

    return numberPlate
  },

  formatBirthday: (birthday) => {
    const parts = birthday.split('-')
    const yyyy = parts[0]
    const mm = parts[1]
    const dd = parts[2]

    return `${dd}-${mm}-${yyyy}`
  },
  to_vietnamese: (number) => {
    let str = parseInt(number) + ''
    let i = 0
    let arr = []
    let index = str.length
    let result = []
    let rsString = ''
    if (index === 0 || str === 'NaN') {
      return ''
    }
    while (index >= 0) {
      arr.push(str.substring(index, Math.max(index - 3, 0)))
      index -= 3
    }
    for (i = arr.length - 1; i >= 0; i--) {
      if (arr[i] !== '' && arr[i] !== '000') {
        result.push(convert_block_three(arr[i]))
        if (dvBlock[i]) {
          result.push(dvBlock[i])
        }
      }
    }
    rsString = result.join(' ')
    let amountText = rsString
      .replace(/[0-9]/g, '')
      .replace(/ /g, ' ')
      .replace(/ $/, '')

    amountText =
      amountText.slice(0, 1).toUpperCase() +
      amountText.slice(1, amountText.length)
    return amountText
  },
}

export default Helper
