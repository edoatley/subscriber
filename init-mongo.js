db.createUser(
  {
    user: "ed",
    password: "supersecret",
    roles: [
      {
        role: "readWrite",
        db: "edadmin"
      }
    ]
  }
)