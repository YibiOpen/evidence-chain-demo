import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

/* Layout */
import Layout from "@/layout";

export const constantRoutes = [
  {
    path: "/",
    component: () => import("@/views/index/index"),
    hidden: true
  },
  {
    path: "/login",
    component: () => import("@/views/login/index"),
    hidden: true
  },
  {
    path: "/404",
    component: () => import("@/views/404"),
    hidden: true
  },
  {
    path: "/product/list",
    component: Layout,
    redirect: "/product/list/table",
    children: [
      {
        path: "table",
        name: "product",
        component: () => import("@/views/product/index"),
        meta: { title: "产品管理", icon: "product" }
      }
    ]
  },
  {
    path: "/step/list",
    component: Layout,
    redirect: "/step/list/table",
    children: [
      {
        path: "table",
        name: "step",
        component: () => import("@/views/step/index"),
        meta: { title: "节点管理", icon: "step" }
      }
    ]
  },
  {
    path: "/field/list",
    component: Layout,
    redirect: "/field/list/table",
    children: [
      {
        path: "table",
        name: "field",
        component: () => import("@/views/field/index"),
        meta: { title: "要素管理", icon: "field" }
      },
      {
        path: "create",
        name: "Create",
        component: () => import("@/views/field/create/index"),
        meta: { title: "要素管理 / 新建", icon: "Create" },
        hidden: true
      }
    ]
  },
  {
    path: "/data",
    component: Layout,
    children: [
      {
        path: "list",
        name: "data",
        component: () => import("@/views/data/index"),
        meta: { title: "证据管理", icon: "data" }
      }
    ]
  },
  {
    path: "/save",
    component: Layout,
    children: [
      {
        path: "index",
        name: "save",
        component: () => import("@/views/save/index"),
        meta: { title: "模拟存证", icon: "form" }
      }
    ]
  },
  {
    path: "/user",
    component: Layout,
    children: [
      {
        path: "index",
        name: "pay",
        component: () => import("@/views/user/index"),
        meta: { title: "个人中心", icon: "user" }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: "*", redirect: "/404", hidden: true }
];

const createRouter = () =>
  new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
  });

const router = createRouter();

export function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher; // reset router
}

export default router;
