/*
    Copyright 2013, Strategic Gains, Inc.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/
package com.strategicgains.restexpress.plugin.swagger;

import com.strategicgains.restexpress.plugin.swagger.annotations.ApiModelRequest;
import org.restexpress.Request;
import org.restexpress.Response;

/**
 * @author toddf
 * @since Nov 22, 2013
 */
public class DummyController
{
	public DummyModel read(Request request, Response response)
	{
        return null;
	}

	public void readAll(Request request, Response response)
	{
	}

    @ApiModelRequest(model = DummyModel[].class)
	public void create(Request request, Response response)
	{
		response.setResponseCreated();
	}

	public void update(Request request, Response response)
	{
	}

	public void delete(Request request, Response response)
	{
		response.setResponseNoContent();
	}

	public void health(Request request, Response response)
	{
	}

	public void options(Request request, Response response)
	{
	}
}
