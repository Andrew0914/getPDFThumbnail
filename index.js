// dependencies
const AWS = require("aws-sdk");
const fs = require("fs");

// get reference to S3 client
const s3 = new AWS.S3();

exports.handler = async (event, context, callback) => {
  const srcBucket = event.Records[0].s3.bucket.name;

  // Object key may have spaces or unicode non-ASCII characters.
  const srcKey = decodeURIComponent(
    event.Records[0].s3.object.key.replace(/\+/g, " ")
  );

  try {
    const params = {
      Bucket: srcBucket,
      Key: srcKey,
    };
    var origFile = await s3.getObject(params).promise();

    var type = origFile.ContentType;
    if (type === "application/pdf") {
      fs.writeFileSync(`/tmp/${srcKey}`, origFile.Body);

      var PDFImage = require("pdf-image").PDFImage;

      var pdfImage = new PDFImage(`/tmp/${srcKey}`);

      const imagePath = await pdfImage.convertPage(0);

      console.log(imagePath);
      // Upload the thumbnail image to the destination bucket
      try {
        const outBuffer = fs.readFileSync(imagePath);
        const destparams = {
          Bucket: srcBucket,
          Key: srcKey + "-thumbnail.png",
          Body: outBuffer,
          ContentType: "image",
        };
        const putResult = await s3.putObject(destparams).promise();
        console.log(putResult);
      } catch (error) {
        console.log(error);
        return;
      }
    }
  } catch (error) {
    console.error(error);
    return;
  }
};
