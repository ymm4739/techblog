const getters = {
  user: state => {
    return {
      username: state.user.username,
      email: state.user.email,
      isValidEmail: state.user.isValidEmail
    }
  },
  token: state => state.user.token,
  timeout: state => state.user.timeout
}

export default getters
