import Vue from 'vue'
import VueRouter from 'vue-router'

import LoginView from "../views/LoginView.vue";
import SignupView from "../views/SignupView.vue";
import PasswordChoice from "../views/user/PasswordChoice.vue";
import PasswordChoiceEmail from "../views/user/PasswordChoiceEmail.vue";
import PasswordChoiceEmailVerification from "../views/user/PasswordChoiceEmailVerification.vue";
import PasswordChange from "../views/user/PasswordChange.vue";
import ErrorComponent from "../components/ErrorComponent.vue";

import Home from '../views/Home.vue'

import NotFoundComponent from '../components/NotFoundComponent'

Vue.use(VueRouter)

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/error', name: 'Error', component: ErrorComponent },
  { path: '/login', name: 'Login', component: LoginView },
  { path: '/signup', name: 'Signup', component: SignupView },
  { path: '*', component: NotFoundComponent },
  { path: "/user/password_choice", name: "PasswordChoice", component: PasswordChoice },
  { path: "/user/password_choice_email", name: "PasswordChoiceEmail", component: PasswordChoiceEmail },
  { path: "/user/password_choice_email_verification", name: "PasswordChoiceEmailVerification", component: PasswordChoiceEmailVerification },
  { path: "/user/password_change", name: "PasswordChange", component: PasswordChange },
]

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  console.log('to : ', to)
  console.log('from : ', from)
  
  next()
  // const pages = [
  //   'Signup', 'Login', 'Home'
  // ]

  // const needNotAuthPages = ['Signup', 'Login']

  // const authRequired = !publicPages.includes(to.name)
  // const unauthRequired = needNotAuthPages.includes(to.name)


})

export default router
