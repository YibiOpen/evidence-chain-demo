<template>
  <div class="app-container">
    <div class="user-info">
      <el-collapse v-model="activeNames">
        <el-collapse-item title="用户名" name="1">
          <div>{{ userData.uname }}</div>
        </el-collapse-item>
        <el-collapse-item title="用户链上地址" name="2">
          <div>{{ userData.publicKey }}</div>
        </el-collapse-item>
        <el-collapse-item title="合约名称" name="3">
          <div>{{ userData.contractName }}</div>
        </el-collapse-item>
        <el-collapse-item title="合约创建时间" name="4">
          <div>{{ userData.createTimeStr }}</div>
        </el-collapse-item>
        <el-collapse-item title="合约地址" name="5">
          <div style="color:red;font-weight:blod;font-size:16px">
            {{ userData.contractAddress }}
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
import { getUser } from '@/api/table'
import { getUserId } from '@/utils/auth'

export default {
  data() {
    return {
      userData: {},
      activeNames: ['1', '2', '3', '4', '5', '6', '7']
    }
  },
  created() {
    this.getUserInfo()
  },
  methods: {
    getUserInfo() {
      const id = getUserId()
      getUser(id).then(res => {
        this.userData = res.data
      })
    }
  }
}
</script>

<style lang="scss">
.el-collapse {
  font-size: 16px;
}
</style>
