/**
 * $Revision $
 * $Date $
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rayo.core.xml.providers;

import java.net.URISyntaxException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

import com.rayo.core.verb.*;

public class ColibriProvider extends BaseProvider {

    // XML -> Object
    // ================================================================================

    private static final Namespace NAMESPACE = new Namespace("", "urn:xmpp:rayo:colibri:1");

    private static final QName COLIBRI_QNAME = new QName("colibri", NAMESPACE);

    @Override
    protected Object processElement(Element element) throws Exception
    {
        if (COLIBRI_QNAME.equals(element.getQName())) {
            return buildColibriCommand(element);
        }
        return null;
    }

    private Object buildColibriCommand(Element element) throws URISyntaxException
    {
		String videobridge = element.attributeValue("videobridge");
 		ColibriCommand command = new ColibriCommand();
		command.setVideobridge(videobridge);

        return command;
    }


    // Object -> XML
    // ================================================================================

    @Override
    protected void generateDocument(Object object, Document document) throws Exception {

		if (object instanceof ColibriCommand) {
            createColibriCommand((ColibriCommand) object, document);

        } else if (object instanceof AddSourceEvent) {
            createAddSourceEvent((AddSourceEvent) object, document);

        } else if (object instanceof RemoveSourceEvent) {
            createRemoveSourceEvent((RemoveSourceEvent) object, document);

        } else if (object instanceof MutedEvent) {
            createMutedEvent((MutedEvent) object, document);

        } else if (object instanceof UnmutedEvent) {
            createUnmutedEvent((UnmutedEvent) object, document);
        }
    }

    private void createColibriCommand(ColibriCommand command, Document document) throws Exception
    {
        Element root = document.addElement(new QName("colibri", NAMESPACE));
		root.addAttribute("videobridge", command.getVideobridge());
    }

    private void createAddSourceEvent(AddSourceEvent event, Document document)
    {
        document.addElement(new QName("addsource", NAMESPACE));
    }

    private void createRemoveSourceEvent(RemoveSourceEvent event, Document document)
    {
        document.addElement(new QName("removesource", NAMESPACE));
    }

    private void createMutedEvent(MutedEvent muted, Document document)
    {
        document.addElement(new QName("onmute", NAMESPACE));
    }

    private void createUnmutedEvent(UnmutedEvent unmuted, Document document)
    {
        document.addElement(new QName("offmute", NAMESPACE));
    }

}