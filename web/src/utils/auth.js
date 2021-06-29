import Cookies from 'js-cookie'

const TokenKey = 'vue_admin_template_token1'
const userId = 'user_id'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function setUserId(userid) {
  return Cookies.set(userId, userid)
}

export function getUserId() {
  return Cookies.get(userId)
}

export function removeUserId() {
  return Cookies.remove(userId)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
