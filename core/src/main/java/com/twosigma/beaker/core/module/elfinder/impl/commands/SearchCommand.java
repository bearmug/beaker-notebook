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
package com.twosigma.beaker.core.module.elfinder.impl.commands;

import com.twosigma.beaker.core.module.elfinder.impl.AbstractJsonCommand;
import com.twosigma.beaker.core.module.elfinder.service.Command;
import com.twosigma.beaker.core.module.elfinder.service.FsService;
import com.twosigma.beaker.core.module.elfinder.util.FsItemFilterUtils;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class SearchCommand extends AbstractJsonCommand
  implements Command {
  @Override
  public void execute(FsService fsService, HttpServletRequest request,
                      ServletContext servletContext, JSONObject json) throws Exception {
    json.put(
      "files",
      files2JsonArray(request, FsItemFilterUtils.filterFiles(
        fsService.find(FsItemFilterUtils.createFileNameKeywordFilter(request.getParameter("q"))),
        FsItemFilterUtils.createFilterFromRequest(request))));
  }
}
