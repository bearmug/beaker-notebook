/*
 *  Copyright 2014 TWO SIGMA OPEN SOURCE, LLC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.twosigma.beaker;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.twosigma.beaker.jvm.serialization.BeakerObjectConverter;
import com.twosigma.beaker.jvm.serialization.ObjectDeserializer;

public class BeakerProgressUpdate {
  private final static Logger logger = Logger.getLogger(BeakerProgressUpdate.class.getName());
  public final String message;
  public final int progressBar;
  public final Object payload;
  
  public BeakerProgressUpdate()
  {
    message = "";
    progressBar = -1;
    payload = null;
  }

  public BeakerProgressUpdate(String m)
  {
    message = m;
    progressBar = -1;
    payload = null;
  }

  public BeakerProgressUpdate(int pb)
  {
    message = "";
    progressBar = pb>=0 && pb<=100 ? pb : pb%100;
    payload = null;
  }

  public BeakerProgressUpdate(String m, int pb)
  {
    message = m;
    progressBar = pb>=0 && pb<=100 ? pb : pb%100;
    payload = null;
  }

  public BeakerProgressUpdate(Object p)
  {
    message = "";
    progressBar = -1;
    payload = p;
  }

  public BeakerProgressUpdate(String m, Object p)
  {
    message = m;
    progressBar = -1;
    payload = p;
  }

  public BeakerProgressUpdate(int pb, Object p)
  {
    message = "";
    progressBar = pb>=0 && pb<=100 ? pb : pb%100;
    payload = p;
  }

  public BeakerProgressUpdate(String m, int pb, Object p)
  {
    message = m;
    progressBar = pb>=0 && pb<=100 ? pb : pb%100;
    payload = p;
  }

  private BeakerProgressUpdate(String m, Integer pb, Object p)
  {
    message = m!=null ? m : "";
    progressBar = pb!=null ? (pb>=0 && pb<=100 ? pb : pb%100) : -1;
    payload = p;
  }

  public static class Serializer extends JsonSerializer<BeakerProgressUpdate> {

    private final Provider<BeakerObjectConverter> objectSerializerProvider;

    @Inject
    private Serializer(Provider<BeakerObjectConverter> osp) {
      objectSerializerProvider = osp;
    }

    private BeakerObjectConverter getObjectSerializer() {
      return objectSerializerProvider.get();
    }

    @Override
    public void serialize(BeakerProgressUpdate value,
        JsonGenerator jgen,
        SerializerProvider provider)
        throws IOException, JsonProcessingException {

      synchronized (value) {
        jgen.writeStartObject();
        jgen.writeStringField("type", "BeakerProgressUpdate");
        jgen.writeStringField("message", value.message);
        jgen.writeNumberField("progressBar", value.progressBar);
        Object obj = value.payload;
        if (obj != null) {
          jgen.writeFieldName("payload");          
          if (!getObjectSerializer().writeObject(obj, jgen))
            jgen.writeString("ERROR: unsupported object "+obj.toString());
        }
        jgen.writeEndObject();
      }
    }
  }

  public static class DeSerializer implements ObjectDeserializer {

    private final Provider<BeakerObjectConverter> objectSerializerProvider;

    @Inject
    private DeSerializer(Provider<BeakerObjectConverter> osp) {
      objectSerializerProvider = osp;
    }

    private BeakerObjectConverter getObjectSerializer() {
      return objectSerializerProvider.get();
    }

    @Override
    public Object deserialize(JsonNode n, ObjectMapper mapper) {
      BeakerProgressUpdate o = null;
      try {
        String message=null;
        Object payload=null;
        Integer progressBar=null;
        
        if (n.has("message"))
          message = n.get("message").asText();
        if (n.has("progressBar"))
          progressBar = n.get("progressBar").asInt();

        if (n.has("payload"))
          payload = getObjectSerializer().deserialize(n.get("payload"), mapper);
        
        o = new BeakerProgressUpdate(message,progressBar,payload);
      } catch (Exception e) {
        logger.log(Level.SEVERE, "exception deserializing BeakerProgressUpdate ", e);
      }
      return o;
    }

    @Override
    public boolean canBeUsed(JsonNode n) {
      return n.has("type") && n.get("type").asText().equals("BeakerProgressUpdate");
    }
  }     
  
}
