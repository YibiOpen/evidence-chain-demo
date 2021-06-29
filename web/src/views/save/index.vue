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
          <el-select v-model="dynamicValidateForm.productId" @change="changeProduct" class="select-32" clearable
                     style="width: 455px"
          >
            <el-option v-for="item in productList" :key="item.productName" :label="item.productName" :value="item.id">
              <span class="font-color-ed5454">{{ item.productName }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="stepId" label="节点">
          <el-select v-model="dynamicValidateForm.stepId" @change="changeStep" class="select-32" clearable
                     style="width: 455px"
          >
            <el-option v-for="item in stepList" :key="item.stepName" :label="item.stepName" :value="item.id">
              <span class="font-color-ed5454">{{ item.stepName }}</span>
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item prop="userName" label="用户名称">
          <el-input v-model="dynamicValidateForm.userName" style="width: 455px"></el-input>
        </el-form-item>

        <el-form-item prop="identType" label="证件类型">
          <el-select v-model="dynamicValidateForm.identType" class="select-32" clearable style="width: 455px">
            <el-option label="身份证" :value="'身份证'"></el-option>
            <el-option label="组织机构代码" :value="'组织机构代码'"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item prop="identNo" label="证件号">
          <el-input v-model="dynamicValidateForm.identNo" style="width: 455px"></el-input>
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
              >
                <el-input :value="chain.chName" :disabled="true"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                  label=""
                  :prop="'itemList.' + index + '.enValue'"
                  :rules="createRules.enValue"
              >
                <el-input
                    v-model="chain.enValue"
                    placeholder="要素值"
                >
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="存证文件">
          <el-upload
              class="upload-demo"
              action=""
              :http-request="uploadFile"
              :auto-upload="true"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              multiple
              :limit="3"
              :on-exceed="handleExceed"
              :file-list="fileList"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="margin-left: 10px" @click="submitForm('dynamicValidateForm')">提交
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { bqSave, getFieldSelectList, getProductSelectList, getStepSelectList, uploadFile } from '@/api/table'

export default {
  data() {
    return {
      createRules: {
        productId: [{ required: true, message: '请选择产品', trigger: 'blur' }],
        stepId: [{ required: true, message: '请选择节点', trigger: 'blur' }],
        userName: [{ required: true, message: '请输入用户名称', trigger: 'blur' }],
        identNo: [{ required: true, message: '请输入证件号', trigger: 'blur' }],
        identType: [{ required: true, message: '请选择证件类型', trigger: 'blur' }],
        enValue: [{ required: true, message: '请输入要素值', trigger: 'blur' }]
      },
      fileList: [],
      productList: [],
      stepList: [],
      dynamicValidateForm: {
        itemList: [
          {
            enName: 'snNo',
            chName: '存证流水号',
            enValue: ''
          }
        ],
        addedFileList: [],
        userName: '',
        identNo: '',
        identType: '',
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
      this.dynamicValidateForm.itemList.forEach((item, index) => {
        this.removeChain(item, index)
      })
      let reqData = {
        productId: val
      }
      getStepSelectList(reqData).then(response => {
        this.stepList = response.data
      })
    },
    changeStep: function(val) {
      this.dynamicValidateForm.itemList.forEach((item, index) => {
        this.removeChain(item, index)
      })
      this.dynamicValidateForm.stepId = val
      let reqData = {
        stepId: val
      }
      getFieldSelectList(reqData).then(response => {
        response.data.forEach((item) => {
          this.addChain(item)
        })
      })
    },
    handleRemove(file, fileList) {
      try {
        this.dynamicValidateForm.addedFileList.forEach((item, index) => {
          if (item.fileName === file.name) {
            this.dynamicValidateForm.addedFileList.splice(index, 1)
            throw Error()
          }
        })
      } catch (err) {

      }
      console.log(this.dynamicValidateForm.addedFileList)
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    uploadFile: function(params) {
      let formData = new FormData()
      formData.append('file', params.file)
      formData.append('FileName', params.file.name)
      formData.append('async', false)
      uploadFile(formData).then(response => {
        this.dynamicValidateForm.addedFileList.push({
          fileName: params.file.name,
          fileNo: response.data
        })
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          bqSave(this.dynamicValidateForm).then((res) => {
            if (res.code === 0) {
              this.$confirm('提交成功', '提交成功', {
                confirmButtonText: '返回',
                type: 'success',
                showCancelButton: false,
                closeOnClickModal: false
              }).then(() => {
                this.$router.push({ path: '/data/list' })
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
    addChain(item) {
      this.dynamicValidateForm.itemList.push({
        enName: item.enName,
        enValue: '',
        chName: item.chName
      })
    },
    removeChain(item, index) {
      if (index !== 0) {
        this.dynamicValidateForm.itemList.splice(index)
      }
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

  .upload-demo {
    margin-left: 10px;
  }

  .el-select {
    display: block;
  }
}
</style>
