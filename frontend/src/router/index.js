import Vue from 'vue'
import VueRouter from 'vue-router'

import LoginView from "../views/LoginView.vue";
import SignupView from "../views/SignupView.vue";
import PasswordChoice from "../views/user/PasswordChoice.vue";

import Home from '../views/Home.vue'

import NotFoundComponent from '../components/NotFoundComponent'

Vue.use(VueRouter)

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/login', name: 'Login', component: LoginView },
  { path: '/signup', name: 'Signup', component: SignupView },
  { path: '*', component: NotFoundComponent },
  { path: "/user/password_choice", name: "PasswordChoice", component: PasswordChoice },
]

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
})

// router.beforeEach((to, from, next) => {

  // const pages = [
  //   'Signup', 'Login', 'Home'
  // ]

  // const needNotAuthPages = ['Signup', 'Login']

  // const authRequired = !publicPages.includes(to.name)
  // const unauthRequired = needNotAuthPages.includes(to.name)


// })

export default router
