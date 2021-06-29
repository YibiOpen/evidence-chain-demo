<template>
  <div>
    <div class="create-form">
      <el-form
          :model="dynamicValidateForm"
          :rules="createRules"
          ref="dynamicValidateForm"
          label-width="100px"
          class="demo-dynamic"
      >
        <el-form-item prop="productId" label="产品">
          <el-select v-model="dynamicValidateForm.productId" @change="changeProduct" class="select-32" clearable>
            <el-option v-for="item in productList" :key="item.productName" :label="item.productName" :value="item.id">
              <span class="font-color-ed5454">{{ item.productName }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="stepId" label="节点">
          <el-select v-model="dynamicValidateForm.stepId" @change="changeStep" class="select-32" clearable>
            <el-option v-for="item in stepList" :key="item.stepName" :label="item.stepName" :value="item.id">
              <span class="font-color-ed5454">{{ item.stepName }}</span>
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item
            class="demo-form-inline"
            v-for="(chain, index) in dynamicValidateForm.itemList"
            :label="'存证要素' + index"
            :key="chain.key"
        >
          <el-row>
            <el-col :span="8">
              <el-form-item
                  label=""
                  :prop="'itemList.' + index + '.chName'"
                  :rules="createRules.chName"
              >
                <el-input v-model="chain.chName" placeholder="要素中文名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                  label=""
                  :prop="'itemList.' + index + '.enName'"
                  :rules="createRules.enName"
              >
                <el-input
                    v-model="chain.enName"
                    placeholder="要素英文名"
                >
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="1">
              <div style="padding-left:5px" v-if="!chain.isSelect">
                <el-button type="text" @click="removeChain(chain)"
                >移除
                </el-button
                >
              </div>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('dynamicValidateForm')"
          >提交
          </el-button
          >
          <el-button @click="addChain">新增</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { getProductSelectList, getStepSelectList, createFields } from '@/api/table'

export default {
  data() {
    return {
      createRules: {
        productId: [{ required: true, message: '请选择产品', trigger: 'blur' }],
        stepId: [{ required: true, message: '请选择节点', trigger: 'blur' }],
        chName: [{ required: true, message: '请输入要素中文名', trigger: 'blur' }],
        enName: [{ required: true, message: '请选要素英文名', trigger: 'blur' }]
      },
      productList: [],
      stepList: [],
      dynamicValidateForm: {
        itemList: [
          {
            chName: '',
            enName: ''
          }
        ],
        productId: null,
        stepId: null
      }
    }
  },
  created() {
    getProductSelectList().then(response => {
      this.productList = response.data
    })
  },
  methods: {
    changeProduct: function(val) {
      this.stepList = []
      this.dynamicValidateForm.stepId = null
      let reqData = {
        productId: val
      }
      getStepSelectList(reqData).then(response => {
        this.stepList = response.data
      })
    },
    changeStep: function(val) {
      this.dynamicValidateForm.stepId = val
    },

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.dynamicValidateForm.itemList.length === 0) {
            this.$message({
              message: '存证要素项至少有一项',
              type: 'warning'
            })
            return false
          }
          createFields(this.dynamicValidateForm).then((res) => {
            if (res.code === 0) {
              this.$confirm('提交成功', '提交成功', {
                confirmButtonText: '返回',
                type: 'success',
                showCancelButton: false,
                closeOnClickModal: false
              }).then(() => {
                this.$router.push({ path: '/field/list' })
              })
            }
          })
        } else {
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    removeChain(item) {
      const chainIndex = this.dynamicValidateForm.itemList.indexOf(item)
      if (chainIndex !== -1) {
        this.dynamicValidateForm.itemList.splice(chainIndex, 1)
      }
    },
    addChain() {
      this.dynamicValidateForm.itemList.push({
        chName: '',
        enName: ''
      })
    }
  }
}
</script>

<style lang="scss">
.create-form {
  width: 70%;
  margin: 40px auto;

  .el-col .el-form-item__content,
  .el-input input {
    margin-left: 10px;
  }

  .el-col .el-input input {
    margin-left: 0;
  }

  .el-select {
    display: block;
  }
}
</style>
