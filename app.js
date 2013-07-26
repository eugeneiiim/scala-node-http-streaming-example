var express = require('express');

var app = express();

app.configure(function () {
  app.use(express.bodyParser());
  app.use(express.logger('dev'));
});

app.post('/stream', function(req, res, next) {
  req.setEncoding('utf8');

  req.on('data', function(data) {
    console.log('data: ' + data);
    res.write('received: ' + data + '\n');
  });

  req.on('end', function(data) {
    console.log('end.');
    res.end();
  });
});

var port = 5432;
console.log('Listening on ' + port);
app.listen(port);
