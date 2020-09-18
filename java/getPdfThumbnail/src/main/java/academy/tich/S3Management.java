package academy.tich;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

/**
 * S3Management.
 */
public class S3Management {

  private AmazonS3 s3Client;
  private String srcBucket, srcKey;

  public S3Management(String srcBucket, String srcKey) {
    this.s3Client = AmazonS3ClientBuilder.defaultClient();
    this.srcBucket = srcBucket;
    this.srcKey = srcKey;
  }

  public S3Object getObject() {
    return s3Client.getObject(new GetObjectRequest(this.srcBucket, this.srcKey));
  }

  public void putThumbnail(Thumbnail thumbnail) throws AmazonS3Exception {
    ObjectMetadata meta = new ObjectMetadata();
    meta.setContentLength(thumbnail.lengt);
    meta.setContentType(thumbnail.mimeType);
    s3Client.putObject(this.srcBucket, this.srcKey + ".png", thumbnail.inputStream, meta);
  }

}