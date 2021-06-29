import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/eviChain/product/list',
    method: 'post',
    data
  })
}

export function getProductList(data) {
  return request({
    url: '/eviChain/product/list',
    method: 'post',
    data
  })
}

export function getChainInfo() {
  return request({
    url: '/eviChain/user/chainInfo',
    method: 'post'
  })
}

export function deployContract(data) {
  return request({
    url: '/eviChain/user/deployContract',
    method: 'post',
    data
  })
}

export function addProduct(data) {
  return request({
    url: '/eviChain/product/add',
    method: 'post',
    data
  })
}

export function modifyProduct(data) {
  return request({
    url: '/eviChain/product/modify',
    method: 'post',
    data
  })
}

export function getProductSelectList() {
  return request({
    url: '/eviChain/product/selectList',
    method: 'post'
  })
}

export function getStepSelectList(data) {
  return request({
    url: '/eviChain/step/selectList',
    method: 'post',
    data
  })
}

export function uploadFile(data) {
  return request({
    url: '/eviChain/file/upload',
    method: 'post',
    data
  })
}

export function getFieldSelectList(data) {
  return request({
    url: '/eviChain/field/selectList',
    method: 'post',
    data
  })
}

export function createFields(data) {
  return request({
    url: '/eviChain/field/add',
    method: 'post',
    data
  })
}

export function getEviDataList(data) {
  return request({
    url: '/eviChain/data/list',
    method: 'post',
    data
  })
}

export function getStepList(data) {
  return request({
    url: '/eviChain/step/list',
    method: 'post',
    data
  })
}

export function addStep(data) {
  return request({
    url: '/eviChain/step/add',
    method: 'post',
    data
  })
}

export function modifyStep(data) {
  return request({
    url: '/eviChain/step/modify',
    method: 'post',
    data
  })
}

export function getFieldList(data) {
  return request({
    url: '/eviChain/field/list',
    method: 'post',
    data
  })
}

export function modifyField(data) {
  return request({
    url: '/eviChain/field/modify',
    method: 'post',
    data
  })
}

export function bqSave(data) {
  return request({
    url: '/eviChain/data/bqSave',
    method: 'post',
    data
  })
}

export function validChain(data) {
  return request({
    url: '/eviChain/data/validChain',
    method: 'post',
    data
  })
}

export function previewChain(data) {
  return request({
    url: '/eviChain/data/previewChain',
    method: 'post',
    data
  })
}

export function getTotalInfo() {
  return request({
    url: '/eviChain/data/getTotalInfo',
    method: 'post'
  })
}

export function getUser(userId) {
  return request({
    url: '/eviChain/user/' + userId,
    method: 'post'
  })
}
