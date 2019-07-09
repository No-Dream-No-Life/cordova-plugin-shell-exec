function ShellExec() {
  this.exec = function (cmd, callback) {
    return cordova.exec(callback, function (err) {
      callback({exitStatus: 100, output: err});
    }, "ShellExec", "exec", [cmd]);

  };

  this.setTime = function (time, success, error) {
    cordova.exec(success, error, "ShellExec", "setTime", [time]);
  }
}

window.ShellExec = new ShellExec();
